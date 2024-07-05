$(document).ready(function() {
 // Remplacez par l'ID de l'utilisateur réel

    $.ajax({
        url: 'http://127.0.0.1:8080/api/compte/bytypcpt/1/',
        type: 'GET',
        timeout: 5000,
        success: function(data) {
            console.log("Données récupérées avec succès:", data);

            // Initialiser DataTables avec les données récupérées
            $('#transactionTable').DataTable({
                data: data,
                columns: [
                    { data: 'num_cpt' },
                    { data: 'user.nom' },
                    { data: 'user.prenoms' },
                    { data: 'user.sexe' },
                    { data: 'user.date_nais' },
                    { data: 'user.phoneNumber' },
                    { data: 'solde' },
                    { data: 'date_creation' }
                ],
               buttons: [
                   {
                       extend: 'excelHtml5',
                       text: 'Export Excel',
                       filename: 'transactions_data',
                       exportOptions: {
                           columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] // Index des colonnes à exporter
                       }
                   },
                   {
                       extend: 'print',
                       text: 'Imprimer',
                       exportOptions: {
                           columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] // Index des colonnes à exporter
                       }
                   }
               ]
            });
        },
        error: function(xhr, status, error) {
            console.error('Erreur lors de la récupération des données:', error);
        }
    });
});


// Fonction pour effectuer la requête Ajax et télécharger le fichier Excel
function genererFichierExcel() {
  var xhr = new XMLHttpRequest();

  // Configuration de la requête GET vers votre API
  xhr.open('GET', 'http://127.0.0.1:8080/api/compte/bytypcpt/1/', true);

  // Gestion de la réponse
  xhr.responseType = 'json'; // Indique que la réponse est en JSON

  xhr.onload = function() {
    if (xhr.status === 200) {
      var transactions = xhr.response; // Récupérer les données JSON de la réponse

      // Convertir les données pour Excel
      var formattedData = formatDataForExcel(transactions);

      // Générer et télécharger le fichier Excel
      generateExcelFile(formattedData);
    }
  };

  // Envoyer la requête
  xhr.send();
}


// Écouter le clic sur le bouton
document.getElementById('genererFichier').addEventListener('click', function() {
  genererFichierExcel();
});

// Fonction pour convertir les données JSON en un tableau formaté pour Excel
function formatDataForExcel(data) {
  const formattedData = [];

  // Entête (nom des colonnes)
  const headers = [
    "Numéro de compte",
    "Nom",
    "Prenons",
    "sexe",
    "Date de Naiss",
    "Numero de Telephone",
    "Solde",
    "Date de creation du compte"
  ];
  formattedData.push(headers);

  // Parcours des transactions
  data.forEach(client => {
    const row = [
      client.num_cpt,
      client.user.nom,
      client.user.prenoms,
      client.user.sexe,
      client.user.date_nais,
      client.user.phoneNumber,
      client.user.solde,
      client.date_creation
    ];
    formattedData.push(row);
  });

  return formattedData;
}

// Fonction pour générer et télécharger le fichier Excel
function generateExcelFile(data) {
  const worksheet = XLSX.utils.aoa_to_sheet(data);
  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, worksheet, 'Transactions');

  // Générer un nom de fichier unique
  const fileName = 'client_' + new Date().toISOString().slice(0, 10) + '.xlsx';

  // Télécharger le fichier
  XLSX.writeFile(workbook, fileName);
}

// Écouter le clic sur le bouton pour générer le fichier Excel
document.getElementById('genererFichier').addEventListener('click', function() {
  genererFichierExcel();
});


