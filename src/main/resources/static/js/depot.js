function verifierCompte() {
    var numeroCompte = document.getElementById("numeroCompte").value.trim();

    var resultMessage = document.getElementById("resultMessage");

    if (numeroCompte.length < 16) {
        resultMessage.innerHTML = "Le numéro de compte doit contenir au moins 16 caractères.";
        resultMessage.classList.remove('text-success');
        resultMessage.classList.add('text-danger');
        document.getElementById("montantSection").style.display = "none";
        return;
    }

    var numCptsElement = document.getElementById("numComptes");

    if (numCptsElement) {
        var monCompte = numCptsElement.textContent.trim();
        console.log("Numéro de compte récupéré :", monCompte);
    } else {
        console.error("Élément avec ID 'numComptes' non trouvé.");
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/compte/verifier/" + encodeURIComponent(numeroCompte), true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 ) {
                var responseData = JSON.parse(xhr.responseText);

                if (numeroCompte === monCompte) {
                    console.log(numeroCompte + " " + monCompte);
                    resultMessage.innerHTML = "Vous ne pouvez pas effectuer un transfert vers votre propre compte.";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                    document.getElementById("montantSection").style.display = "none";
                } else {
                    resultMessage.innerHTML = "Compte valide : " + responseData.user.nom + " " + responseData.user.prenoms;
                    resultMessage.classList.remove('text-danger');
                    resultMessage.classList.add('text-success');
                    document.getElementById("montantSection").style.display = "block";
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

function verifierSolde() {
    var numCptsElement = document.getElementById("numComptes");

    if (numCptsElement) {
        var monCompte = numCptsElement.textContent.trim();
        console.log("Numéro de compte récupéré :", monCompte);
    } else {
        console.error("Élément avec ID 'numComptes' non trouvé.");
        return;
    }

    var resultMessage = document.getElementById("resSol");
    var toggleSoldeBtn = document.getElementById("toggleSoldeBtn");

    if (resultMessage.style.display === "none") {
        // Afficher le solde
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "api/compte/solde?numCpt=" + encodeURIComponent(monCompte), true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    var responseData = JSON.parse(xhr.responseText);
                    resultMessage.innerHTML = "Solde du compte : " + responseData.solde + " XOF";
                    resultMessage.classList.remove('text-danger');
                    resultMessage.classList.add('text-success');
                } else {
                    resultMessage.innerHTML = "Impossible de récupérer le solde du compte.";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                }
                resultMessage.style.display = "block";
                toggleSoldeBtn.innerText = "MASQUER SOLDE";
            }
        };
        xhr.send();
    } else {
        // Masquer le solde
        resultMessage.style.display = "none";
        toggleSoldeBtn.innerText = "VOIR SOLDE";
    }
}





function transfererFonds(event) {
    var sourceCompte = $('#numComptes').text().trim(); // Assurez-vous que cela obtient le bon numéro de compte source
    var destinationCompte = $('#numeroCompte').val().trim(); // Assurez-vous que cela obtient le bon numéro de compte de destination
    var montant = $('#montant').val().trim();

    console.log(sourceCompte);
    console.log(destinationCompte);
    console.log(montant);

    if (montant < 1000) {
        alert("Le montant doit être supérieur ou égal à 1000.");
        return; // Arrête l'exécution de la fonction si le montant est inférieur à 1000
    }

    $.ajax({
        url: '/api/compte/transfer',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: {
            sourceCompte: sourceCompte,
            destinationCompte: destinationCompte,
            montant: montant
        },
        success: function (response) {
            alert("Transfert réussi!");
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : error;
            alert("Échec du transfert. Erreur: " + errorMessage);
        }
    });
}


function toggleSolde() {
    var montantSection = document.getElementById("montantSections");
    var toggleSoldeBtn = document.getElementById("toggleSoldeBtn");

    if (montantSection.style.display === "none") {
        montantSection.style.display = "block";
        toggleSoldeBtn.innerText = "MASQUER SOLDE";
    } else {
        montantSection.style.display = "none";
        toggleSoldeBtn.innerText = "VOIR SOLDE";
    }
}

function resetVerification() {
    var resultMessage = document.getElementById("resultMessage");
    resultMessage.innerHTML = "";
    document.getElementById("montantSection").style.display = "none";
}
