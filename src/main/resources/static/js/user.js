$(document).ready(function() {
    console.log("ok");
    $("#submitBtn").click(function() {
        // Récupérer les données du formulaire
        var formData = $("#registerForm").serializeArray();
        
        // Convertir les données du formulaire en objet JSON
        var jsonData = {};
        $.each(formData, function(index, field) {
            jsonData[field.name] = field.value;
        });

        // Afficher les données dans la console
        console.log("Données du formulaire :", jsonData);

        // Envoyer les données du formulaire au backend via AJAX au format JSON
        $.ajax({
            type: "POST",
            url: "api/Utilisateurs",
            contentType: "application/json",
            data: JSON.stringify(jsonData),
            success: function(response) {
                // Traitement de la réponse si nécessaire
                console.log("Réponse du backend :", response);
            },
            error: function(xhr, status, error) {
                // Gestion des erreurs
                console.error("Erreur lors de l'envoi des données :", error);
            }
        });
    });
});
