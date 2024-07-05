$(document).ready(function() {
 // Remplacez par l'ID de l'utilisateur réel

    $.ajax({
        url: '/api/transactions',
        type: 'GET',
        timeout: 5000,
        success: function(data) {
            console.log("Données récupérées avec succès:", data);

            // Initialiser DataTables avec les données récupérées
            $('#transactionTable').DataTable({
                data: data,
                columns: [
                    { data: 'id_trans' },
                    { data: 'date_trans' },
                    { data: 'time_trans' },
                    { data: 'cpt_exp.num_cpt' },
                    { data: 'cpt_dest.num_cpt' },
                    { data: 'montant_trans' },
                    { data: 'frais_trans' },
                    { data: 'typeTransaction.lib_type_trans' },
                    { data: 'lieu.lib_type_cpt' },
                    { data: 'status.lib_status' },
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
  xhr.open('GET', '/api/transactions', true);

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
    "ID Transaction",
    "Date Transaction",
    "Montant",
    "Frais",
    "Heure Transaction",
    "Compte Expéditeur",
    "Compte Destinataire",
    "Type Transaction",
    "Lieu",
    "Statut"
  ];
  formattedData.push(headers);

  // Parcours des transactions
  data.forEach(transaction => {
    const row = [
      transaction.id_trans,
      transaction.date_trans,
      transaction.montant_trans,
      transaction.frais_trans,
      transaction.time_trans,
      transaction.cpt_exp.num_cpt,
      transaction.cpt_dest.num_cpt,
      transaction.typeTransaction.lib_type_trans,
      transaction.lieu.lib_type_cpt,
      transaction.status.lib_status
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
  const fileName = 'transactions_' + new Date().toISOString().slice(0, 10) + '.xlsx';

  // Télécharger le fichier
  XLSX.writeFile(workbook, fileName);
}

// Écouter le clic sur le bouton pour générer le fichier Excel
document.getElementById('genererFichier').addEventListener('click', function() {
  genererFichierExcel();
});
