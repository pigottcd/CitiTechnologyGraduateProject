// returns a html table created from json data
function createTable(data) {
    let tableBody = '<table class="table table-sm" id="strategyTable"><caption>Strategies</caption><thead><tr>';
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

//returns a 2d list of chart data created from json data
function createChartData(data) {
    let chartData =[[],[]];
    data.forEach(function(elm) {
        // elm.colName dependent on json data
        chartData[0].push(elm.ticker);
        chartData[1].push(elm.id);
    });
    return chartData;
}

// returns a 2d list of chart data created from a given row in an html table
function getRowData(row) {
    let rowData = $(row).children("td").map(function () {
        return $(this).text();
    }).get();
    let newRowData = [[], []];
    //categories, rowData[1] is a name
    newRowData[0].push(rowData[2]);
    // values, rowData[0] is a number
    newRowData[1].push(parseInt(rowData[0]));
    return newRowData;
}

// run code once the page has loaded
$(document).ready(function() {

    // start ajax call, then process the data
    $.ajax({
        url: "http://localhost:8081/strat/",
        crossOrigin: true
    }).then(function(data) {

        // create table
        let tableBody = createTable(data);

        // push table to strategyTable div tag
        $('#strategyTableDiv').html(tableBody);
        $('#strategyTable').DataTable();

        // create data for chart
        // work on implementation here, redundant json processing (already processed for table)
        let chartData  = createChartData(data); //categories:chartData[0], values:chartData[1]

        // create and push chart to myChart chart tag
        let ctx = $('#strategyGraph')[0].getContext('2d');
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: chartData[0],
                datasets: [{
                    label: 'User ids',
                    data: chartData[1],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });

        // runs when a row is clicked
        $('.clickableRow').click(function() {

            // get data from row
            rowData = getRowData(this);

            // destroys a chart
            myChart.destroy();

            // create a new chart with data form the clicked row
            let ctx = document.getElementById("strategyGraph").getContext('2d');
            myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: rowData[0],
                    datasets: [{
                        label: 'Ticker ID',
                        data: rowData[1],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero:true
                            }
                        }]
                    }
                }
            });

        });
    });
});