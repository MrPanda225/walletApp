document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/frs')
        .then(response => response.json())
        .then(data => {
            let fournisseurSelect = document.getElementById('fournisseurSelect');
            data.forEach(fournisseur => {
                let option = document.createElement('option');
                option.value = fournisseur.id_fournisseur;
                option.textContent = fournisseur.lib_fournisseur;
                fournisseurSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching fournisseurs:', error));

    document.getElementById('fournisseurSelect').addEventListener('change', function() {
        let fournisseurId = this.value;
        if (fournisseurId) {
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
                                // Récupérer le montant du service
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
        } else {
            document.getElementById('servicesList').innerHTML = '';
            document.getElementById('servicesSection').style.display = 'none';
            document.getElementById('informationSection').style.display = 'none';
        }
    });

});


function verifierCompte() {
        var numeroCompte = document.getElementById("numeroCompte").value.trim();
        console.log(numeroCompte)

        var resultMessage = document.getElementById("resultMessage");

        if (numeroCompte.length < 16) {
            resultMessage.innerHTML = "Le numéro de compte doit contenir au moins 16 caractères.";
            resultMessage.classList.remove('text-success');
            resultMessage.classList.add('text-danger');
            document.getElementById("montantSection").style.display = "none";
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
                    } else {
                        resultMessage.innerHTML = "Compte invalide";
                        resultMessage.classList.remove('text-success');
                        resultMessage.classList.add('text-danger');
                        document.getElementById("montantSection").style.display = "none";
                    }
                } else {
                    resultMessage.innerHTML = "Compte invalide";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                    document.getElementById("montantSection").style.display = "none";
                }
            }
        };
        xhr.send();
    }

