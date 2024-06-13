$(document).ready(function() {



      var userId = $('#id_user').val();

    function fetchNotifications() {
        $.ajax({
            url: '/api/notifications/unread?userId=' + userId,
            type: 'GET',
            success: function(data) {
            console.log("ok")
                var notificationsList = $('#Notif1 .timeline');
                notificationsList.empty(); // Clear existing notifications

                if (data.length > 0) {
                    data.forEach(function(notification) {
                        var transactionId = notification.transaction.id_trans;
                        console.log(transactionId)
                        var notificationHtml = '<li>' +
                            '<div class="timeline-panel">' +
                                '<div class="media me-2">' +
                                    '<img alt="image" width="50" src="images/avatar/1.jpg">' + // Replace with dynamic image if available
                                '</div>' +
                                '<div class="media-body">' +
                                    '<h6 class="mb-1">' + notification.message + '</h6>' +
                                    '<small class="d-block">' + new Date(notification.createdAt).toLocaleString() + '</small>';
                        if (transactionId) {
                            notificationHtml += '<button class="btn btn-primary mt-2" onclick="validateTransaction(' + transactionId + ')">Valider</button>';
                        }
                        notificationHtml += '</div>' +
                            '</div>' +
                        '</li>';
                        notificationsList.append(notificationHtml);
                    });
                } else {
                    notificationsList.append('<li><div class="timeline-panel"><div class="media-body"><h6 class="mb-1">Aucune notification</h6></div></div></li>');
                }
            },
            error: function() {
                alert('Erreur lors de la récupération des notifications');
            }
        });
    }

    window.validateTransaction = function(transactionId) {

        fetch(`/api/notifications/validation/${transactionId}`, {
            method: 'PUT'
        }).then(response => {
            if (response.ok) {
                alert('Transaction validée avec succès');
                fetchNotifications(); // Rafraîchit les notifications après validation
            } else {
                alert('Erreur lors de la validation de la transaction');
            }
        });
    }

    fetchNotifications();

    setInterval(fetchNotifications, 60000);
});

$(document).ready(function() {
    function updateSolde() {
        var numCpt = $('#numCptt').val();
        $.ajax({
            url: '/api/compte/solde?numCpt=' + numCpt,
            type: 'GET',
            success: function(data) {
            console.log(data)
                $('#soldeCompteUpdate').text(data.solde.toFixed(2) + ' XOF'); // Met à jour le solde sur la page
            },
            error: function() {
                console.error('Erreur lors de la récupération du solde du compte');
            }
        });
    }
    updateSolde();

    // Met à jour le solde toutes les X secondes (par exemple, toutes les 30 secondes)
    setInterval(updateSolde, 7000); // Mettez la durée de rafraîchissement souhaitée en millisecondes
});

function fetchTransactions() {
var numCpt = $('#numCptt').val();
        $.ajax({
            url: '/api/transactions/user/' + numCpt,
            type: 'GET',
            success: function(data) {
                var transactionsTable = $('.previous-transactions tbody');
                transactionsTable.empty(); // Clear existing transactions

                if (data.length > 0) {
                    data.forEach(function(transaction) {
                        var transactionRow = '<tr>' +
                            '<td>' +
                                '<svg width="63" height="63" viewBox="0 0 63 63" fill="none" xmlns="http://www.w3.org/2000/svg">' +
                                    '<rect x="1.00002" y="1" width="61" height="61" rx="29" stroke="#2BC155" stroke-width="2" />' +
                                    '<g clip-path="url(#clip0)">' +
                                        '<path d="M35.2219 42.9875C34.8938 42.3094 35.1836 41.4891 35.8617 41.1609C37.7484 40.2531 39.3453 38.8422 40.4828 37.0758C41.6477 35.2656 42.2656 33.1656 42.2656 31C42.2656 24.7875 37.2125 19.7344 31 19.7344C24.7875 19.7344 19.7344 24.7875 19.7344 31C19.7344 33.1656 20.3523 35.2656 21.5117 37.0813C22.6437 38.8477 24.2461 40.2586 26.1328 41.1664C26.8109 41.4945 27.1008 42.3094 26.7727 42.993C26.4445 43.6711 25.6297 43.9609 24.9461 43.6328C22.6 42.5063 20.6148 40.7563 19.2094 38.5578C17.7656 36.3047 17 33.6906 17 31C17 27.2594 18.4547 23.743 21.1016 21.1016C23.743 18.4547 27.2594 17 31 17C34.7406 17 38.257 18.4547 40.8984 21.1016C43.5453 23.7484 45 27.2594 45 31C45 33.6906 44.2344 36.3047 42.7852 38.5578C41.3742 40.7508 39.3891 42.5063 37.0484 43.6328C36.3648 43.9555 35.55 43.6711 35.2219 42.9875Z" fill="#2BC155" />' +
                                        '<path d="M36.3211 31.7274C36.5891 31.9953 36.7203 32.3453 36.7203 32.6953C36.7203 33.0453 36.5891 33.3953 36.3211 33.6633L32.8812 37.1031C32.3781 37.6063 31.7109 37.8797 31.0055 37.8797C30.3 37.8797 29.6273 37.6008 29.1297 37.1031L25.6898 33.6633C25.1539 33.1274 25.1539 32.2633 25.6898 31.7274C26.2258 31.1914 27.0898 31.1914 27.6258 31.7274L29.6437 33.7453L29.6437 25.9742C29.6437 25.2196 30.2562 24.6071 31.0109 24.6071C31.7656 24.6071 32.3781 25.2196 32.3781 25.9742L32.3781 33.7508L34.3961 31.7328C34.9211 31.1969 35.7852 31.1969 36.3211 31.7274Z" fill="#2BC155" />' +
                                    '</g>' +
                                    '<defs>' +
                                        '<clipPath id="clip0">' +
                                            '<rect width="28" height="28" fill="white" transform="matrix(-4.37114e-08 1 1 4.37114e-08 17 17)" />' +
                                        '</clipPath>' +
                                    '</defs>' +
                                '</svg>' +
                            '</td>' +
                            '<td>' +
                                '<h6 class="fs-16 font-w600 mb-0"><a href="transactions-details.html" class="text-black">' + transaction.description + '</a></h6>' +
                                '<span class="fs-14">' + transaction.type + '</span>' +
                            '</td>' +
                            '<td>' +
                                '<h6 class="fs-16 text-black font-w400 mb-0">' + new Date(transaction.date).toLocaleDateString() + '</h6>' +
                                '<span class="fs-14">' + new Date(transaction.date).toLocaleTimeString() + '</span>' +
                            '</td>' +
                            '<td><span class="fs-16 text-black font-w500">' + (transaction.amount > 0 ? '+' : '') + transaction.amount + '</span></td>' +
                            '<td><span class="text-success fs-16 font-w500 text-end d-block">' + transaction.status + '</span></td>' +
                        '</tr>';
                        transactionsTable.append(transactionRow);
                    });
                } else {
                    transactionsTable.append('<tr><td colspan="5">Aucune transaction trouvée.</td></tr>');
                }
            },
            error: function() {
                console.error('Erreur lors de la récupération des transactions');
            }
        });
    }

    $(document).ready(function() {

    var cptExpId = $('#numCptt').val();
    var cptDestId = $('#numCptt').val();
        function fetchTransactionsByExp() {
            $.ajax({
                url: '/api/transactions/exp/' + cptExpId,
                type: 'GET',
                success: function(data) {
                    updateTransactionsTable(data, 'exp');
                },
                error: function() {
                    console.error('Erreur lors de la récupération des transactions expéditeur');
                }
            });
        }

        function fetchTransactionsByDest() {
            $.ajax({
                url: '/api/transactions/dest/' + cptDestId,
                type: 'GET',
                success: function(data) {
                    updateTransactionsTable(data, 'dest');
                },
                error: function() {
                    console.error('Erreur lors de la récupération des transactions destinataire');
                }
            });
        }

        function updateTransactionsTable(data, type) {
            var transactionsTable = $('.previous-transactions tbody');
            transactionsTable.empty(); // Clear existing transactions

            if (data.length > 0) {
                data.forEach(function(transaction) {
                    var message = type === 'exp' ? 'Vous avez envoyé' : 'Vous avez reçu';
                    var transactionRow = '<tr>' +
                        '<td>' +
                            '<svg width="63" height="63" viewBox="0 0 63 63" fill="none" xmlns="http://www.w3.org/2000/svg">' +
                                '<rect x="1.00002" y="1" width="61" height="61" rx="29" stroke="#2BC155" stroke-width="2" />' +
                                '<g clip-path="url(#clip0)">' +
                                    '<path d="M35.2219 42.9875C34.8938 42.3094 35.1836 41.4891 35.8617 41.1609C37.7484 40.2531 39.3453 38.8422 40.4828 37.0758C41.6477 35.2656 42.2656 33.1656 42.2656 31C42.2656 24.7875 37.2125 19.7344 31 19.7344C24.7875 19.7344 19.7344 24.7875 19.7344 31C19.7344 33.1656 20.3523 35.2656 21.5117 37.0813C22.6437 38.8477 24.2461 40.2586 26.1328 41.1664C26.8109 41.4945 27.1008 42.3094 26.7727 42.993C26.4445 43.6711 25.6297 43.9609 24.9461 43.6328C22.6 42.5063 20.6148 40.7563 19.2094 38.5578C17.7656 36.3047 17 33.6906 17 31C17 27.2594 18.4547 23.743 21.1016 21.1016C23.743 18.4547 27.2594 17 31 17C34.7406 17 38.257 18.4547 40.8984 21.1016C43.5453 23.7484 45 27.2594 45 31C45 33.6906 44.2344 36.3047 42.7852 38.5578C41.3742 40.7508 39.3891 42.5063 37.0484 43.6328C36.3648 43.9555 35.55 43.6711 35.2219 42.9875Z" fill="#2BC155" />' +
                                    '<path d="M36.3211 31.7274C36.5891 31.9953 36.7203 32.3453 36.7203 32.6953C36.7203 33.0453 36.5891 33.3953 36.3211 33.6633L32.8812 37.1031C32.3781 37.6063 31.7109 37.8797 31.0055 37.8797C30.3 37.8797 29.6273 37.6008 29.1297 37.1031L25.6898 33.6633C25.1539 33.1274 25.1539 32.2633 25.6898 31.7274C26.2258 31.1914 27.0898 31.1914 27.6258 31.7274L29.6437 33.7453L29.6437 25.9742C29.6437 25.2196 30.2562 24.6071 31.0109 24.6071C31.7656 24.6071 32.3781 25.2196 32.3781 25.9742L32.3781 33.7508L34.3961 31.7328C34.9211 31.1969 35.7852 31.1969 36.3211 31.7274Z" fill="#2BC155" />' +
                                '</g>' +
                            '</svg>' +
                        '</td>' +
                        '<td>' +
                            '<h6 class="fs-16 font-w600 mb-0"><a href="transactions-details.html" class="text-black">' + transaction.cpt_dest + '</a></h6>' +
                            '<span class="fs-14">' + message + ' ' + transaction.montant_trans + ' XOF</span>' +
                        '</td>' +
                        '<td>' +
                            '<h6 class="fs-16 text-black font-w400 mb-0">' + new Date(transaction.date_trans).toLocaleDateString() + '</h6>' +
                            '<span class="fs-14">' + new Date(transaction.date_trans).toLocaleTimeString() + '</span>' +
                        '</td>' +
                        '<td><span class="fs-16 text-black font-w500">' + (transaction.montant_trans > 0 ? '+' : '') + transaction.montant_trans + ' XOF</span></td>' +
                        '<td><span class="text-success fs-16 font-w500 text-end d-block">' + transaction.status.lib_status + '</span></td>' +
                    '</tr>';
                    transactionsTable.append(transactionRow);
                });
            } else {
                transactionsTable.append('<tr><td colspan="5">Aucune transaction trouvée.</td></tr>');
            }
        }

        // Appel initial pour récupérer et afficher les transactions
        fetchTransactionsByExp();  // Si vous voulez afficher les transactions de l'expéditeur
        fetchTransactionsByDest(); // Si vous voulez afficher les transactions du destinataire
    });
