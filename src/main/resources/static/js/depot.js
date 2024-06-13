function verifierCompte() {
    var numeroCompte = document.getElementById("numeroCompte").value;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/compte/verifier/" + numeroCompte, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            var resultMessage = document.getElementById("resultMessage");
            if (xhr.status === 200) {
                var responseData = JSON.parse(xhr.responseText);
                resultMessage.innerHTML = "Compte valide. Nom : " + responseData.user.nom + " Prénom : " + responseData.user.prenoms;
                resultMessage.classList.remove('text-danger');
                resultMessage.classList.add('text-success');
                document.getElementById("montantSection").style.display = "block";
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

 function transfererFonds() {
        var sourceCompte = document.getElementById("numCpt").innerText;
        var destinationCompte = document.getElementById("numeroCompte").value;
        var montant = document.getElementById("montant").value;

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/api/compte/transfer", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200 || xhr.status === 500) {
                    alert("Transfert réussi!");
                } else {
                    alert("Échec du transfert.");
                }
            }
        };
        xhr.send("sourceCompte=" + sourceCompte + "&destinationCompte=" + destinationCompte + "&montant=" + montant);
    }

function resetVerification() {
    var resultMessage = document.getElementById("resultMessage");
    resultMessage.innerHTML = "";
    document.getElementById("montantSection").style.display = "none";
}
