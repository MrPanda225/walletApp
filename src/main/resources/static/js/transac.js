$(document).ready(function() {
    var accountId = $('#numComptes').val();

    function renderTable(transactions) {
        var transactionTableBody = $('#transactionTableBod');
        transactionTableBody.empty(); // Clear existing rows

        transactions.forEach(function(transaction) {
            var cptExp = transaction.cpt_exp;
            var cptDest = transaction.cpt_dest;

            // Déterminer le nom de l'expéditeur
            var expediteur = '';
            if (cptExp.user) {
                expediteur = `${cptExp.user.nom} ${cptExp.user.prenoms}`; // Si l'expéditeur est un utilisateur
            } else if (cptExp.agence) {
                expediteur = `Agence: ${cptExp.agence.lib_agence}`; // Si l'expéditeur est une agence
            }

            // Déterminer le nom du destinataire
           var destinataire = '';
                        if (cptDest.user) {
                            destinataire = `Destinataire: ${cptDest.user.nom} ${cptDest.user.prenoms}`;
                        } else if (cptDest.fournisseur) {
                            destinataire = `Destinataire: ${cptDest.fournisseur.lib_fournisseur}`;
                        } else {
                            destinataire = `Destinataire: ${cptDest.agence.lib_agence}`;
                        }

             var statusClass = '';
                                var iconSvg = '';
                                if (transaction.status.lib_status.toLowerCase() === "passed") {
                                    statusClass = 'text-success';
                                    iconSvg = '<svg width="30" height="30" viewBox="0 0 63 63" fill="none" xmlns="http://www.w3.org/2000/svg"><rect x="1.00002" y="1" width="61" height="61" rx="29" stroke="#2BC155" stroke-width="2"/><g clip-path="url(#clip9)"><path d="M35.2219 42.9875C34.8938 42.3094 35.1836 41.4891 35.8617 41.1609C37.7484 40.2531 39.3453 38.8422 40.4828 37.0758C41.6477 35.2656 42.2656 33.1656 42.2656 31C42.2656 24.7875 37.2125 19.7344 31 19.7344C24.7875 19.7344 19.7344 24.7875 19.7344 31C19.7344 33.1656 20.3523 35.2656 21.5117 37.0813C22.6437 38.8477 24.2461 40.2586 26.1328 41.1664C26.8109 41.4945 27.1008 42.3094 26.7727 42.993C26.4445 43.6711 25.6297 43.9609 24.9461 43.6328C22.6 42.5063 20.6148 40.7563 19.2094 38.5578C17.7656 36.3047 17 33.6906 17 31C17 27.2594 18.4547 23.743 21.1016 21.1016C23.743 18.4547 27.2594 17 31 17C34.7406 17 38.257 18.4547 40.8984 21.1016C43.5453 23.7484 45 27.2594 45 31C45 33.6906 44.2344 36.3047 42.7852 38.5578C41.3742 40.7508 39.3891 42.5063 37.0484 43.6328C36.3648 43.9555 35.55 43.6711 35.2219 42.9875Z" fill="#2BC155"/><path d="M36.3211 31.7274C36.5891 31.9953 36.7203 32.3453 36.7203 32.6953C36.7203 33.0453 36.5891 33.3953 36.3211 33.6633L32.8812 37.1031C32.3781 37.6063 31.7109 37.8797 31.0055 37.8797C30.3 37.8797 29.6273 37.6008 29.1297 37.1031L25.6898 33.6633C25.1539 33.1274 25.1539 32.2633 25.6898 31.7274C26.2258 31.1914 27.0898 31.1914 27.6258 31.7274L29.6437 33.7453L29.6437 25.9742C29.6437 25.2196 30.2562 24.6071 31.0109 24.6071C31.7656 24.6071 32.3781 25.2196 32.3781 25.9742L32.3781 33.7508L34.3961 31.7328C34.9211 31.1969 35.7852 31.1969 36.3211 31.7274Z" fill="#2BC155"/></g><defs><clipPath id="clip9"><rect width="28" height="28" fill="white" transform="matrix(-4.37114e-08 1 1 4.37114e-08 17 17)"/></clipPath></defs></svg>';
                                } else if (transaction.status.lib_status.toLowerCase() === "no passed") {
                                    statusClass = 'text-danger';
                                    iconSvg = '<svg width="30" height="30" viewBox="0 0 63 63" fill="none" xmlns="http://www.w3.org/2000/svg"><rect x="1" y="1" width="61" height="61" rx="29" stroke="#FF2E2E" stroke-width="2"/><g clip-path="url(#clip10)"><path d="M35.2219 19.0125C34.8937 19.6906 35.1836 20.5109 35.8617 20.8391C37.7484 21.7469 39.3453 23.1578 40.4828 24.9242C41.6476 26.7344 42.2656 28.8344 42.2656 31C42.2656 37.2125 37.2125 42.2656 31 42.2656C24.7875 42.2656 19.7344 37.2125 19.7344 31C19.7344 28.8344 20.3523 26.7344 21.5117 24.9187C22.6437 23.1523 24.2461 21.7414 26.1328 20.8336C26.8109 20.5055 27.1008 19.6906 26.7727 19.007C26.4445 18.3289 25.6297 18.0391 24.9461 18.3672C22.6 19.4938 20.6148 21.2438 19.2094 23.4422C17.7656 25.6953 17 28.3094 17 31C17 34.7406 18.4547 38.257 21.1016 40.8984C23.743 43.5453 27.2594 45 31 45C34.7406 45 38.257 43.5453 40.8984 40.8984C43.5453 38.2516 45 34.7406 45 31C45 28.3094 44.2344 25.6953 42.7852 23.4422C41.3742 21.2492 39.3891 19.4938 37.0484 18.3672C36.3648 18.0445 35.55 18.3289 35.2219 19.0125Z" fill="#FF2E2E"/><path d="M27.6258 31.7273C27.3578 31.9953 27.0062 32.1266 26.6562 32.1266C26.3062 32.1266 25.9555 31.9953 25.6875 31.7273C25.1539 31.1914 25.1539 30.3273 25.6875 29.7914L29.1273 26.3515C29.6305 25.8484 30.2977 25.575 31.0031 25.575C31.7086 25.575 32.3813 25.8539 32.8789 26.3515L36.3187 29.7914C36.8547 30.3273 36.8547 31.1914 36.3187 31.7273C35.7828 32.2633 34.9187 32.2633 34.3828 31.7273L32.365 29.7094L32.365 37.4805C32.365 38.2352 31.7523 38.8477 30.9977 38.8477C30.243 38.8477 29.6305 38.2352 29.6305 37.4805L29.6305 29.7141L27.6125 31.732C27.0867 32.2679 26.2227 32.2679 25.6867 31.7375L27.6258 31.7273Z" fill="#FF2E2E"/></g><defs><clipPath id="clip10"><rect width="28" height="28" fill="white" transform="matrix(-4.37114e-08 1 1 4.37114e-08 17 17)"/></clipPath></defs></svg>';
                                } else {
                                    statusClass = 'text-warning';
                                    iconSvg = '<svg width="30" height="30" viewBox="0 0 63 63" fill="none" xmlns="http://www.w3.org/2000/svg"><rect x="1.00002" y="1" width="61" height="61" rx="29" stroke="#F1B90B" stroke-width="2"/><g clip-path="url(#clip8)"><path d="M29.6633 33.4875C30.057 33.8812 30.6906 33.8812 31.0843 33.4875L33.0945 31.4773L35.1047 33.4875C35.4984 33.8812 36.132 33.8812 36.5257 33.4875C36.9194 33.0938 36.9194 32.4602 36.5257 32.0664L34.5155 30.0562L36.5257 28.0461C36.9194 27.6523 36.9194 27.0187 36.5257 26.625C36.132 26.2312 35.4984 26.2312 35.1047 26.625L33.0945 28.6352L31.0843 26.625C30.6906 26.2312 30.057 26.2312 29.6633 26.625C29.2695 27.0187 29.2695 27.6523 29.6633 28.0461L31.6734 30.0562L29.6633 32.0664C29.2695 32.4602 29.2695 33.0938 29.6633 33.4875Z" fill="#F1B90B"/><path d="M31 46C24.7875 46 19.7344 40.9469 19.7344 34.7344C19.7344 32.5688 20.3523 30.4688 21.5117 28.6523C22.6437 26.8859 24.2461 25.475 26.1328 24.5672C26.8109 24.2391 27.1008 23.4242 26.7727 22.7406C26.4445 22.0617 25.6297 21.7719 24.9461 22.1C22.6 23.2266 20.6148 24.9766 19.2094 27.175C17.7656 29.4281 17 32.0422 17 34.7328C17 38.4734 18.4547 41.99 21.1016 44.6313C23.743 47.2781 27.2594 48.7328 31 48.7328C34.7406 48.7328 38.257 47.2781 40.8984 44.6313C43.5453 41.9844 45 38.4734 45 34.7328C45 32.0422 44.2344 29.4281 42.7852 27.175C41.3742 24.982 39.3891 23.2266 37.0484 22.1C36.3648 21.7773 35.55 22.0617 35.2219 22.7453C34.8938 23.4234 35.1836 24.2438 35.8617 24.5719C37.7484 25.4797 39.3453 26.8906 40.4828 28.657C41.6477 30.4672 42.2656 32.5672 42.2656 34.7328C42.2656 40.9469 37.2125 46 31 46Z" fill="#F1B90B"/></g><defs><clipPath id="clip8"><rect width="28" height="28" fill="white" transform="matrix(-4.37114e-08 1 1 4.37114e-08 17 17)"/></clipPath></defs></svg>';
                                }

            var row = '<tr>' +
                '<td>' + transaction.id_trans + '</td>' +
                '<td>' + iconSvg + '</td>' +
                '<td>' + expediteur + '</td>' +
                '<td>' + destinataire + '</td>' +
                '<td>' + transaction.date_trans + ' ' + transaction.time_trans + '</td>' +
                '<td>' + transaction.typeTransaction.lib_type_trans + '</td>' +
                '<td>' + transaction.montant_trans + ' XOF</td>' +
                '<td  class="' + statusClass + '">' + transaction.status.lib_status + '</td>' +
                '<td>' +
                '<div class="dropdown mb-auto">' +
                '<div class="btn-link" role="button" data-bs-toggle="dropdown" aria-expanded="false">' +
                '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">' +
                '<path d="M10 11.9999C10 13.1045 10.8954 13.9999 12 13.9999C13.1046 13.9999 14 13.1045 14 11.9999C14 10.8954 13.1046 9.99994 12 9.99994C10.8954 9.99994 10 10.8954 10 11.9999Z" fill="black"></path>' +
                '<path d="M10 4.00006C10 5.10463 10.8954 6.00006 12 6.00006C13.1046 6.00006 14 5.10463 14 4.00006C14 2.89549 13.1046 2.00006 12 2.00006C10.8954 2.00006 10 2.89549 10 4.00006Z" fill="black"></path>' +
                '<path d="M10 20C10 21.1046 10.8954 22 12 22C13.1046 22 14 21.1046 14 20C14 18.8954 13.1046 18 12 18C10.8954 18 10 18.8954 10 20Z" fill="black"></path>' +
                '</svg>' +
                '</div>' +
                '<div class="dropdown-menu dropdown-menu-end">' +

                '<a class="dropdown-item" href="/detail_trans?id_trans= ' + transaction.id_trans +' ">Facture</a>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '</tr>';

            transactionTableBody.append(row);
        });
    }

    function fetchTransactions() {
        $.get("/api/transactions/account/" + accountId, function(data) {
         transactions = data.sort((a, b) => b.id_trans - a.id_trans);
            renderTable(data);
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error fetching transactions:", textStatus, errorThrown);
        });
    }
    fetchTransactions();


});
