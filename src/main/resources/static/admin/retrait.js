$(document).ready(function() {
    $('#row2').hide();

    $('#validateNumCpt').click(function(event) {
        event.preventDefault();
        var numCpt = $('#num_cpt').val();

        $.ajax({
            url: 'http://127.0.0.1:8080/api/compte/' + numCpt,
            type: 'GET',
            success: function(data) {
                if (data.user != null) {
                    $('#numCpt').val(data.num_cpt);
                    $('#nomClient').val(data.user.nom);
                    $('#prenomsClient').val(data.user.prenoms);
                    $('#row2').show();
                } else {
                    alert('Compte non trouvé');
                }
            },
            error: function() {
                alert('Erreur lors de la récupération des informations du compte');
            }
        });
    });

    $('#retraitBtn').click(function(event) {
        event.preventDefault();
        var cptExp = $('#exp').val(); // Ensure this gets the correct source account number
        var cptDes = $('#num_cpt').val(); // Ensure this gets the correct destination account number
        var montant = $('#montant').val();

        console.log(cptExp)
          console.log(cptDes)
            console.log(montant)

        $.ajax({
            url: 'http://127.0.0.1:8080/api/compte/retrait',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: {
                cpt_exp: cptExp,
                cpt_des: cptDes,
                montant: montant
            },
            success: function (response) {
                $('#row2').show();
                $('#resultMessage').text(response.message);
            },
            error: function (xhr, status, error) {
                $('#row2').show();
                $('#resultMessage').text('Erreur: ' + xhr.responseJSON.message);
            }
        });
    });
});
