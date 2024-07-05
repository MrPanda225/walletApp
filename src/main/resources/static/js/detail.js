$(document).ready(function() {
    var transactionId = $('#transactionId').val();
    var CptId = $('#numCp').val();
    var user = $('#id_user').val();

    function fetchTransactionDetail(transactionId) {
        $.get("/api/transactions/" + transactionId, function(data) {

            $('#date').text(data.date_trans);
            $('#time').text(data.time_trans);
            $('#balance').text(data.montant_trans + ' XOF');

            var expediteur = '';
            if (data.cpt_exp.user) {
                expediteur = `${data.cpt_exp.user.nom} ${data.cpt_exp.user.prenoms}`;
            } else if (data.cpt_exp.agence) {
                expediteur = `Agence: ${data.cpt_exp.agence.lib_agence}`;
            }

            var destinataire = '';
            if (data.cpt_dest.user) {
                destinataire = `${data.cpt_dest.user.nom} ${data.cpt_dest.user.prenoms}`;
            } else if (data.cpt_dest.agence) {
                destinataire = `Agence: ${data.cpt_dest.agence.lib_agence}`;
            }

            $('#expediteur').text(expediteur);
            $('#destinataire').text(destinataire);
            $('#transactionLibs').text(data.typeTransaction.lib_type_trans);
            var message = '';

            if (expediteur && destinataire) {
                if(data.typeTransaction.lib_type_trans=="transfert")
                {
                    if(data.cpt_exp.user.id_user == user) {
                        message = 'Vous ('+expediteur+') avez envoyé  un montant de '+ data.montant_trans +' XOF à  '+destinataire+'. Nous vous remerçions pour la confiance accordez .';
                    }

                     if(data.cpt_exp.user.id_user != user) {
                        message = 'Vous ('+destinataire+') avez reçu un montant de '+ data.montant_trans +' XOF de la part de '+expediteur+'. Nous vous remerçions pour la confiance accordez .';
                     }

                }
                if(data.typeTransaction.lib_type_trans=="rétrait")
                {
                    message = 'Vous (' + destinataire +') avez retiré '+ data.montant_trans  +' XOF de '+ expediteur +'. Nous vous remerçions pour la confiance accordez .';
                }
                 if(data.typeTransaction.lib_type_trans=="dépot")
                {
                   message = 'Vous (' + destinataire +') avez reçu un dépot de  '+ data.montant_trans  +' XOF de '+ expediteur +'. Nous vous remerçions pour la confiance accordez .';
                }
            }

            else if (expediteur) {
                message = `Message de ${expediteur}.`;
            } else if (destinataire) {
                message = `Message pour ${destinataire}.`;
            } else {
                message = 'Message par défaut pour tous les autres cas.';
            }

            $('#message').text(message);

        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error fetching transaction details:", textStatus, errorThrown);
        });
    }

    fetchTransactionDetail(transactionId);
});
