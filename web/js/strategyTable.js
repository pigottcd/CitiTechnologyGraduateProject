// returns a html table created from json data
function createTable(data) {
    let tableBody = '<table class="table table-striped table-sm"><thead><tr>';
    for (value in data[0]) {
        tableBody+='<th>';
        tableBody+=value;
        tableBody+='</th>';
    }
    tableBody+='</tr></thead><tbody>';
    for (idx = 0; idx < data.length; idx++) {
        tableBody += '<tr class="clickableRow">';
        for (value in data[0]) {
            tableBody += '<td>';
            tableBody += data[idx][value];
            tableBody += '</td>';
        }
        tableBody += '</tr>';
    }
    tableBody+='</tbody></table>';
    return tableBody;
}

$(document).ready(function() {

    // start ajax call, then process the data
    $.ajax({
        url: "http://localhost:8081/",
        crossOrigin: true
    }).then(function(data) {

        // create table
        let tableBody = createTable(data);

        // push table to usersTable div tag
        $('#strategyTable').html(tableBody);
    })
});