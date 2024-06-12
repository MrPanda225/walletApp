$(document).ready(function() {
    var userId = 3; // ID de l'utilisateur actuel

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
        fetch(`/api/notifications/validate/${transactionId}`, {
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
