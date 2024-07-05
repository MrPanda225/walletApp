function verifierCompte() {
    var numeroCompte = document.getElementById("numeroCompte").value;
    var monCompte = document.getElementById("numCpts").value;
    console.log(numeroCompte + " "+monCompte)
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/api/compte/verifier/" + numeroCompte, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            var resultMessage = document.getElementById("resultMessage");

            if (xhr.status === 200) {
                var responseData = JSON.parse(xhr.responseText);

                // Vérifier si les deux comptes sont identiques
                if (numeroCompte === monCompte) {
                    resultMessage.innerHTML = "Vous ne pouvez pas effectuer un transfert vers votre propre compte.";
                    resultMessage.classList.remove('text-success');
                    resultMessage.classList.add('text-danger');
                    document.getElementById("montantSection").style.display = "none";
                } else {
                    resultMessage.innerHTML = "Compte valide. Nom : " + responseData.user.nom + " Prénom : " + responseData.user.prenoms;
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



function transfererFonds(event) {

    var sourceCompte = $('#numCpts').text().trim(); // Assurez-vous que cela obtient le bon numéro de compte source
    var destinationCompte = $('#numeroCompte').val().trim(); // Assurez-vous que cela obtient le bon numéro de compte de destination
    var montant = $('#montant').val().trim();

      console.log(sourceCompte)
       console.log(destinationCompte)
       console.log(montant)

    $.ajax({
        url: 'http://localhost:8080/api/compte/transfer',
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
