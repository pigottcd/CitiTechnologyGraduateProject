//returns a 2d list of chart data created from json data
/*function createChartData(data) {
    let chartData =[[],[]];
    data.forEach(function(elm) {
        // elm.colName dependent on json data
        chartData[0].push(elm.name);
        chartData[1].push(elm.id);
    });
    return chartData;
}*/

// returns a 2d list of chart data created from a given row in an html table
function getRowData(row) {
    let rowData = $(row).children("td").map(function () {
        return $(this).text();
    }).get();
    let newRowData = [[], []];
    //categories
    newRowData[0].push(rowData[1]);
    // values
    newRowData[1].push(parseInt(rowData[0]));
    return newRowData;
}

function writeGraph(canvas, data) {

    // create graph configuration
    let config =  {
        type: 'bar',
        data: {
            labels: data[0],
            datasets: [{
                label: 'Strategy',
                data: data[1],
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
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    strategyGraph = new Chart(canvas, config);

}

function writeButtons() {
    let buttons = '<form id="deleteStrategy" class="mx-auto">' +
        '<button type="submit" class="btn">Delete</button>' +
        '</form>' +
        '<button class="btn mx-auto " id="cloneStrategy">Clone</button>';
    $('#graphButtons').html(buttons);
}

// run code once the page has loaded
$(document).ready(function() {

    if ($('#strategyTable').length) {

        // graph first row on page load (might want last row, for when a user adds a new strategy)
        // pull first row DOM object
        let firstRow = $('#strategyTable tr:first');

        // grab data from row
        let graphData = getRowData(firstRow); //categories:chartData[0], values:chartData[1]

        // pull graph canvas DOM object
        let graphCanvas = $('#strategyGraph')[0].getContext('2d');

        // push the graph
        writeGraph(graphCanvas, graphData);

        // push clone and delete buttons
        writeButtons();

        // runs when a row is clicked
        $('.clickableRow').click(function () {

            // get data from clicked row
            let graphData = getRowData(this);

            // pull graph canvas DOM object
            let graphCanvas = $('#strategyGraph')[0].getContext('2d');

            // clear chart
            strategyGraph.destroy();

            // create a new chart with data form the clicked row
            writeGraph(graphCanvas, graphData);

            });
        }
    }
);