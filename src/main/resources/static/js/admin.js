function openModal(agenceId) {
    document.getElementById('agenceIdInput').value = agenceId;
    $('#updateSoldeModal').modal('show');
}

function updateSolde() {
    const agenceId = document.getElementById('agenceIdInput').value;
    const newSolde = document.getElementById('soldeInput').value;

    // Envoyer la demande de mise à jour au serveur (AJAX)
    fetch(`/api/agences/${agenceId}/updateSolde`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ solde: newSolde }),
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Actualiser le solde dans le tableau
            document.querySelector(`tr[data-agence-id="${agenceId}"] td.solde`).textContent = `${newSolde} XOF`;
            $('#updateSoldeModal').modal('hide');
        } else {
            alert('Erreur lors de la mise à jour du solde');
        }
    })
    .catch(error => console.error('Error:', error));
}
