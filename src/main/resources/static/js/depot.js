function verifierCompte() {
    var numeroCompte = document.getElementById("numeroCompte").value;
    var numCptsElement = document.getElementById("numComptes");

    if (numCptsElement) {
        var monCompte = numCptsElement.textContent.trim();
        console.log("Numéro de compte récupéré :", numeroCompte);
    } else {
        console.error("Élément avec ID 'numCpts' non trouvé.");
        return;
    }

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/api/compte/verifier/" + numeroCompte, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            var resultMessage = document.getElementById("resultMessage");

            if (xhr.status === 200 ) {
                var responseData = JSON.parse(xhr.responseText);

                if (numeroCompte === monCompte) {
                    console.log(numeroCompte + " " + monCompte);
                    resultMessage.innerHTML = "Vous ne pouvez pas effectuer un transfert vers votre propre compte.";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                    document.getElementById("montantSection").style.display = "none";
                } else {
                    resultMessage.innerHTML = "Compte valide : " + responseData.user.nom + " Prénom : " + responseData.user.prenoms;
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
            console.error("Élément avec ID 'numCpts' non trouvé.");
            return;
        }

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "api/compte/solde?numCpt=" + encodeURIComponent(monCompte), true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            var resultMessage = document.getElementById("resSol");
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
        }
    };
    xhr.send();
}





function transfererFonds(event) {

    var sourceCompte = $('#numComptes').text().trim(); // Assurez-vous que cela obtient le bon numéro de compte source
    var destinationCompte = $('#numeroCompte').val().trim(); // Assurez-vous que cela obtient le bon numéro de compte de destination
    var montant = $('#montant').val().trim();

      console.log(sourceCompte)
       console.log(destinationCompte)
       console.log(montant)

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

function resetVerification() {
    var resultMessage = document.getElementById("resultMessage");
    resultMessage.innerHTML = "";
    document.getElementById("montantSection").style.display = "none";
}
