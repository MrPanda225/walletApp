document.addEventListener('DOMContentLoaded', function() {
    chargerFournisseurs();

    document.getElementById('fournisseurSelect').addEventListener('change', function() {
        let fournisseurId = this.value;
        if (fournisseurId) {
            chargerServices(fournisseurId);
        } else {
            viderServices();
        }
    });

   document.getElementById('validerButton').addEventListener('click', function() {
           verifierCompte();
           afficherMontantServiceSelectionne();
           afficherCptFournisseur();

           // Effectuer une requête AJAX pour le paiement au fournisseur
           var sourceCompte = document.getElementById('numeroCompte').value.trim();
           var destCompte = document.getElementById('cptFournisseurAffiche').textContent.trim();
           var montant = parseFloat(document.getElementById('montantAffiche').textContent.split(" ")[0]);

                         $.ajax({
                                    url: '/api/compte/paiementfrs',
                                    type: 'POST',
                                    contentType: 'application/x-www-form-urlencoded',
                                    data: {
                                        sourceCompte: sourceCompte,
                                        destCompte: destCompte,
                                        montant: montant
                                    },
                                    success: function(response) {
                                        alert("Transfert réussi!");
                                    },
                                    error: function(xhr, status, error) {
                                        var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : error;
                                        alert("Échec du transfert. Erreur: " + errorMessage);
                                    }
                                });

       });

});

function chargerFournisseurs() {
    fetch('/api/frs')
        .then(response => response.json())
        .then(data => {
            let fournisseurSelect = document.getElementById('fournisseurSelect');
            fournisseurSelect.innerHTML = '';
            data.forEach(fournisseur => {
                let option = document.createElement('option');
                option.value = fournisseur.id_fournisseur;
                option.textContent = fournisseur.lib_fournisseur;
                option.setAttribute('data-cpt', fournisseur.cpt_fournisseur); // Ajouter le cpt_fournisseur en tant qu'attribut de donnée
                fournisseurSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching fournisseurs:', error));
}

function chargerServices(fournisseurId) {
    fetch(`/api/frs/${fournisseurId}/services`)
        .then(response => response.json())
        .then(data => {
            let servicesList = document.getElementById('servicesList');
            servicesList.innerHTML = '';
            data.forEach(service => {
                let div = document.createElement('div');
                div.className = 'form-check';
                div.innerHTML = `
                    <input class="form-check-input service-checkbox" type="checkbox" value="${service.id_service}" id="service-${service.id_service}">
                    <label class="form-check-label" for="service-${service.id_service}">
                        ${service.nom} | ${service.solde} XOF
                    </label>
                `;
                servicesList.appendChild(div);
            });
            document.getElementById('servicesSection').style.display = 'block';

            document.querySelectorAll('.service-checkbox').forEach(checkbox => {
                checkbox.addEventListener('change', function() {
                    if (this.checked) {
                        console.log('Service ID:', this.value);
                        let serviceMontant = parseFloat(this.parentElement.querySelector('.form-check-label').textContent.split('|')[1].trim());
                        console.log('Montant:', serviceMontant);
                    }

                    if (document.querySelectorAll('.service-checkbox:checked').length > 0) {
                        document.getElementById('informationSection').style.display = 'block';
                    } else {
                        document.getElementById('informationSection').style.display = 'none';
                    }
                });
            });
        })
        .catch(error => console.error('Error fetching services:', error));
}

function viderServices() {
    document.getElementById('servicesList').innerHTML = '';
    document.getElementById('servicesSection').style.display = 'none';
    document.getElementById('informationSection').style.display = 'none';
}

function verifierCompte() {
    var numeroCompte = document.getElementById("numeroCompte").value.trim();
    console.log('Numéro de Compte:', numeroCompte);

    var resultMessage = document.getElementById("resultMessage");

    if (numeroCompte.length < 16) {
        resultMessage.innerHTML = "Le numéro de compte doit contenir au moins 16 caractères.";
        resultMessage.classList.remove('text-success');
        resultMessage.classList.add('text-danger');
        document.getElementById("montantSection").style.display = "none";
        // Masquer le bouton "Valider" si le compte est invalide
        document.getElementById('validerButton').setAttribute('hidden', 'true');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/compte/verifier/" + encodeURIComponent(numeroCompte), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var responseData = JSON.parse(xhr.responseText);
                if (responseData.user) {
                    resultMessage.innerHTML = "Compte valide : " + responseData.user.nom + " " + responseData.user.prenoms;
                    resultMessage.classList.remove('text-danger');
                    resultMessage.classList.add('text-success');
                    document.getElementById("montantSection").style.display = "block";
                    // Afficher le bouton "Valider" lorsque le compte est valide
                    document.getElementById('validerButton').removeAttribute('hidden');
                } else {
                    resultMessage.innerHTML = "Compte invalide";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                    document.getElementById("montantSection").style.display = "none";
                    // Masquer le bouton "Valider" si le compte est invalide
                    document.getElementById('validerButton').setAttribute('hidden', 'true');
                }
            } else {
                resultMessage.innerHTML = "Compte invalide";
                resultMessage.classList.remove('text-success');
                resultMessage.classList.add('text-danger');
                document.getElementById("montantSection").style.display = "none";
                // Masquer le bouton "Valider" si le compte est invalide
                document.getElementById('validerButton').setAttribute('hidden', 'true');
            }
        }
    };
    xhr.send();
}

function afficherMontantServiceSelectionne() {
    let checkedService = document.querySelector('.service-checkbox:checked');
    if (checkedService) {
        let serviceMontant = parseFloat(checkedService.parentElement.querySelector('.form-check-label').textContent.split('|')[1].trim());
        console.log('Montant du service sélectionné:', serviceMontant);
        document.getElementById('montantAffiche').textContent = serviceMontant + " XOF";
    } else {
        console.log('Aucun service sélectionné.');
    }
}

function afficherCptFournisseur() {
    let fournisseurSelect = document.getElementById('fournisseurSelect');
    let selectedOption = fournisseurSelect.options[fournisseurSelect.selectedIndex];
    let cptFournisseur = selectedOption.getAttribute('data-cpt');
    console.log('Compte Fournisseur:', cptFournisseur);
    document.getElementById('cptFournisseurAffiche').textContent = cptFournisseur;
}
