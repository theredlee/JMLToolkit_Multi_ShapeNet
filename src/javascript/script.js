// Difference between pageLoad , onload & $(document).ready(): https://stackoverflow.com/questions/7971615/difference-between-pageload-onload-document-ready#:~:text=The%20ready%20event%20occurs%20after,event%20is%20specific%20to%20jQuery.&text=I%20know%20HTML%20document%20load%20means%20all%20page%20element%20load%20complete.
google.charts.load('current', { packages: ['corechart', 'bar'] });
// google.load('visualization', '1.0', { 'packages': ['corechart'], 'callback': drawCharts });

// Load timeseries dataset
var globalLinesTimeseries = [];
var globalLabelArr = [];
var globalCoefArr = [];
var globalInterceptArr = [];
var globalFeaturesArr = [];
var globalMultiArr = [];
var globalMultiplication = [];

// -------------------------------

var globalLinesShapelet = [];
var globalShapeletWeight = [];
var currentLabelLinesTimeseries = [];
var currentLabelLinesShapelet = [];
var labelSet;

/*------------*/
var currentTimeseriesLabelSelection;
var currentShapeletLabelSelection;
var currentTimeseriesSelection;
var currentShapeletSelection;

/*------------*/
var distanceAll; // A shapelet to all timeseries with the same label
const topK = 5; // Initialize the topK = 5

// The ready event occurs after the HTML document has been loaded, =>
$(document).ready(function () {
    // Asynchronous function involking
    // lineChart();
});

// while the onload event occurs later, when all content (e.g. images) also has been loaded.
window.onload = function () {
    // multiCharts()
    loadTimeseries();
}

function addEventHandlers() {
}

/*----------------------------------------*/
function loadTimeseries() {
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TRAIN.arff",
            dataType: null,
            success: function (data) {
                processData(data, "timeseries");
            }
        });
        // -------------
        $.ajax({
            type: "GET",
            url: "../datasets/ALT_AND_AFP_ARFF/ALT_AND_AFP_TEST.arff",
            dataType: null,
            success: function (data) {
                processData(data, "timeseries");
                // Call Ajax function one by one
                readCoef();
            }
        });
    });
}

function readCoef() {
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/Distance/coef.txt",
            dataType: null,
            success: function (data) {
                processCoef(data);
                // Call Ajax function one by one
                readIntercept();
            }
        });
    });
}

function readIntercept() {
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/Distance/intercept.txt",
            dataType: null,
            success: function (data) {
                processIntercept(data);
                // Call Ajax function one by one
                readFeatures();
            }
        });
    });
}

function readFeatures() {
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/Distance/feature_train.txt",
            dataType: null,
            success: function (data) {
                processFeatures(data);
            }
        });
    });
    // ---------------------------
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/Distance/feature_test.txt",
            dataType: null,
            success: function (data) {
                processFeatures(data);
                // Call the last function - multiplication()
                multiplication();
            }
        });
    });
}

function readMultiplication() {
    $(document).ready(function () {
        // Loading test dataset
        $.ajax({
            type: "GET",
            url: "../datasets/Distance/multiplication.txt",
            dataType: null,
            success: function (data) {
                processMultiplication(data);
            }
        });
    });
}

async function processData(allText, type) {
    var labelIndex = 0; // The first element of each row is the lable in terms of both timeseries and shapelet
    var allTextLines = allText.split(/\r\n|\n/);
    var lines = [];
    var labelSetTmp = new Set();

    var count = 0
    var readStart = false
    allTextLines.forEach(element => {
        // Put it brfore the recognition statement to skip the '@data' row
        if (readStart) {
            var entries = element.split('\\n');
            var firstStrFlag = true
            var len;
            var label;
            // Each element contains two timeseries
            // Structure desgin: [label, [[val1, val2, val3, ...], [val1, val2, val3, ...]]]
            var aMultiTimeseriesArr = []
            var tarr = []

            entries.forEach(entryStrTemp => {
                // Remove the single quote "'"
                var entryStr = entryStrTemp.replaceAll("'", "");
                // Split the string to float
                var entryArrToCut = entryStr.split(',').map(Number);

                if (firstStrFlag) {
                    // Since the last element in the second number array string is the label, we take the length of the first number array string as the reference length 
                    len = entryArrToCut.length;  // how many elements there are in each rows
                    firstStrFlag = false
                }

                // Select the first 15 elements in array
                var valueArr = entryArrToCut.slice(0, len);

                if (entryArrToCut.length > len) {
                    label = entryArrToCut.slice(len, len + 1);
                    // Use unshift to push the label at the first element
                    aMultiTimeseriesArr.unshift(label);
                    // console.log("label: " + label)
                    // console.log('-----')
                    // label format: [val]
                    globalLabelArr.push(label[0]);
                }

                tarr.push(valueArr);
                // Structure of each tarr: [lable, values] (label: [val], values: [val1, val2, val3, ...])
            }
            );

            aMultiTimeseriesArr.push(tarr)
            lines.push(aMultiTimeseriesArr);

            // console.log("-------------------------------- count: " + count)
            count++;
        }

        if (element.includes("@data")) {
            readStart = true
        }
    });

    console.log('globalLabelArr.length: ' + globalLabelArr.length);
    // console.log(globalLabelArr)
}

async function processCoef(allText) {
    var entries = allText.split(', ');
    entries.forEach(entry => {
        globalCoefArr.push(entry);
    });
    console.log("globalCoefArr: " + globalCoefArr);
    // coefEndFlag = true;
}

async function processIntercept(allText) {
    var entries = allText.split(', ');
    entries.forEach(entry => {
        globalInterceptArr.push(entry);
    });
    console.log("globalInterceptArr: " + globalInterceptArr);
    // interceptEndFlag = true;
}

async function processFeatures(allText) {
    var entries = allText.split('\n');
    // console.log(entries.length);
    entries.forEach(entryStr => {
        // Handle the empty line case
        if (entryStr.length != 0) {
            var entry = entryStr.split(' ');
            arr = [];
            entry.forEach(element => {
                arr.push(parseFloat(element));
            });
            globalFeaturesArr.push(arr);
        }
    });
    console.log("globalFeaturesArr.length: " + globalFeaturesArr.length)
    // if (globalFeaturesArr.length == entries.length) {
    //     featureEndFlag1 = true;
    // }else {
    //     featureEndFlag2 = true;
    // }
}

async function processMultiplication(allText) {
    var entries = allText.split('\n');
    entries.forEach(entry => {
        // Handle the empty line case
        if (entry.length > 0) {
            globalMultiplication.push(entry);
        }
    });
    console.log("globalMultiplication.length: " + globalMultiplication.length)
    // multiEndFlag = true;
}

async function multiplication() {
    var count = 0;
    console.log('-------------------');
    // console.log(globalFeaturesArr);
    // console.log(globalCoefArr);
    intercept = globalInterceptArr[0];

    globalFeaturesArr.forEach(featureRow => {
        var rowSum = 0;
        for (var i=0; i < featureRow.length; i++) {
            fVal = featureRow[i];
            coefVal = globalCoefArr[i];
            rowSum = rowSum + parseFloat(fVal * coefVal);
            // console.log('fVal * coefVal: ' + fVal * coefVal);
        }
        rowSum = rowSum + parseFloat(intercept);
        globalMultiArr.push(rowSum);
        count++;
        // console.log('rowSum: ' + rowSum);
    });

    console.log("count: " + count);

    console.log("globalMultiArr: " + globalMultiArr);
}

// ------------------------------------------------------------------------------
// ------------------------------------------------------------------------------

function multiCharts() {
    let chartConfig = {
        theme: 'classic',
        gui: {
            contextMenu: {
                empty: true
            }
        },
        graphset: [
            {
                type: 'hbar',
                backgroundColor: '#363b42',
                borderColor: '#111a21',
                borderWidth: '2px',
                height: '100%',
                title: {
                    text: 'Company Performance',
                    marginTop: '10px',
                    marginLeft: '20px',
                    backgroundColor: 'none',
                    fontSize: '18px',
                    shadow: false,
                    textAlign: 'left'
                },
                plot: {
                    barsOverlap: '100%',
                    barWidth: '17px',
                    thousandsSeparator: ',',
                    animation: {
                        effect: 'ANIMATION_SLIDE_BOTTOM'
                    }
                },
                plotarea: {
                    margin: '50px 25px 20px 25px'
                },
                scaleX: {
                    values: ['Puma', 'Converse', 'Adidas', 'Nike'],
                    guide: {
                        visible: false
                    },
                    item: {
                        color: '#fff',
                        offsetX: '210px',
                        textAlign: 'left',
                        width: '200px'
                    },
                    lineColor: 'none',
                    tick: {
                        visible: false
                    }
                },
                scaleY: {
                    guide: {
                        visible: false
                    },
                    item: {
                        visible: false
                    },
                    lineColor: 'none',
                    tick: {
                        visible: false
                    }
                },
                series: [
                    {
                        values: [103902, 112352, 121823, 154092],
                        tooltip: {
                            shadow: false
                        },
                        hoverState: {
                            visible: false
                        },
                        styles: [
                            {
                                backgroundColor: '#009016'
                            },
                            {
                                backgroundColor: '#017790'
                            },
                            {
                                backgroundColor: '#ee5b18'
                            },
                            {
                                backgroundColor: '#c94742'
                            }
                        ],
                        tooltipText: '$%node-value',
                        zIndex: 2
                    },
                    {
                        values: [300000, 300000, 300000, 300000],
                        valueBox: {
                            text: '$%data-rvalues',
                            fontColor: '#fff',
                            fontSize: '10px',
                            offsetX: '3px',
                            offsetY: '-1px',
                            placement: 'top-in',
                            textAlign: 'right',
                            visible: true
                        },
                        backgroundColor: '#000',
                        dataRvalues: [103902, 112352, 121823, 154092],
                        maxTrackers: 0,
                        zIndex: 1
                    }
                ]
            },
            // -------------------------
            // {
            //     type: 'line',
            //     backgroundColor: '#363b42',
            //     borderColor: '#111a21',
            //     borderWidth: '2px',
            //     height: '50%',
            //     y: '50%',
            //     legend: {
            //         margin: 'auto auto 10px auto',
            //         backgroundColor: 'none',
            //         borderWidth: '0px',
            //         item: {
            //             padding: '3px',
            //             fontColor: '#eee',
            //             fontSize: '12px'
            //         },
            //         layout: 'float',
            //         marker: {
            //             lineWidth: '2px',
            //             showLine: 'true',
            //             size: '4px'
            //         },
            //         shadow: false,
            //         width: '75%',
            //         x: '25%'
            //     },
            //     plot: {
            //         tooltip: {
            //             visible: false
            //         },
            //         animation: {
            //             effect: 'ANIMATION_SLIDE_BOTTOM'
            //         },
            //         tooltipText: 'Items Sold: %v'
            //     },
            //     plotarea: {
            //         margin: '20 30 75 55'
            //     },
            //     scaleX: {
            //         values: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec', 'Dec2', 'Dec3', 'Dec4'],
            //         guide: {
            //             visible: false
            //         },
            //         item: {
            //             paddingTop: '5px',
            //             fontColor: '#FFFFFF',
            //             fontSize: '10px'
            //         },
            //         lineColor: '#fff',
            //         lineWidth: '1px',
            //         tick: {
            //             lineColor: '#fff',
            //             lineWidth: '1px'
            //         }
            //     },
            //     scaleY: {
            //         // values: '0:30:0.5',
            //         guide: {
            //             lineColor: '#7d8285',
            //             lineStyle: 'solid',
            //             lineWidth: '2px'
            //         },
            //         item: {
            //             paddingRight: '5px',
            //             fontColor: '#fff',
            //             fontSize: '10px'
            //         },
            //         lineColor: 'none',
            //         tick: {
            //             visible: false
            //         }
            //     },
            //     crosshairX: {
            //         scaleLabel: {
            //             fontColor: '#000000'
            //         }
            //     },
            //     series: [
            //         // {
            //         //     text: 'Nike',
            //         //     // values: [25.4, 15.3, 26, 21, 24, 26, 33, 25, 15, 25, 22, 24],
            //         //     values: valArr,
            //         //     legendMarker: {
            //         //         type: 'circle',
            //         //         borderColor: '#c94742',
            //         //         size: '5px'
            //         //     },
            //         //     lineColor: '#c94742',
            //         //     lineWidth: '2px',
            //         //     marker: {
            //         //         backgroundColor: '#c94742',
            //         //         borderWidth: '0px',
            //         //         shadow: false,
            //         //         size: '3px'
            //         //     },
            //         //     palette: 3,
            //         //     shadow: false
            //         // },
            //         // {
            //         //     text: 'Adidas',
            //         //     values: [42, 43, 30, 50, 31, 48, 55, 46, 48, 32, 50, 38],
            //         //     legendMarker: {
            //         //         type: 'circle',
            //         //         borderColor: '#ee5b18',
            //         //         size: '5px'
            //         //     },
            //         //     lineColor: '#ee5b18',
            //         //     lineWidth: '2px',
            //         //     marker: {
            //         //         backgroundColor: '#ee5b18',
            //         //         borderWidth: '0px',
            //         //         shadow: false,
            //         //         size: '3px'
            //         //     },
            //         //     palette: 2,
            //         //     shadow: false,
            //         //     visible: true
            //         // },
            //         // {
            //         //     text: 'Converse',
            //         //     values: [51, 53, 47, 60, 48, 52, 75, 52, 55, 47, 60, 48],
            //         //     legendMarker: {
            //         //         type: 'circle',
            //         //         borderColor: '#017790',
            //         //         size: '5px'
            //         //     },
            //         //     lineColor: '#017790',
            //         //     lineWidth: '2px',
            //         //     marker: {
            //         //         backgroundColor: '#017790',
            //         //         borderWidth: '0px',
            //         //         shadow: false,
            //         //         size: '3px'
            //         //     },
            //         //     palette: 1,
            //         //     shadow: false,
            //         //     visible: true
            //         // },
            //         // {
            //         //     text: 'Puma',
            //         //     values: [69, 68, 54, 48, 70, 74, 98, 70, 72, 68, 49, 69],
            //         //     legendMarker: {
            //         //         type: 'circle',
            //         //         borderColor: '#009016',
            //         //         size: '5px'
            //         //     },
            //         //     lineColor: '#009016',
            //         //     lineWidth: '2px',
            //         //     marker: {
            //         //         backgroundColor: '#009016',
            //         //         borderWidth: '0px',
            //         //         shadow: false,
            //         //         size: '3px'
            //         //     },
            //         //     palette: 0,
            //         //     shadow: false
            //         // }
            //     ]
            // },
            // -------------------------
        ]
    };

    zingchart.render({
        id: 'myChart',
        data: chartConfig,
        height: '100%',
        width: '100%',
    });
}

function multiGoogleCharts(aMultiTimeseriesArr) {
    // ----------------------------------------
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'X');
    data.addColumn('number', 'Timeseries_1');

    var valRow = [];
    var xIndex = 0;
    var valArr = aMultiTimeseriesArr[1][0]

    valArr.forEach(val => {
        var arr = []
        arr.push(xIndex++);
        arr.push(val);
        valRow.push(arr);
    });

    //   data.addRows([
    //     [0, 0],   [1, 10],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
    //     [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
    //     [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
    //     [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
    //     [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
    //     [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
    //     [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
    //     [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
    //     [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
    //     [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
    //     [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
    //     [66, 70], [67, 72], [68, 75], [69, 80]
    //   ]);

    data.addRows(valRow);

    var options = {
        legend: { position: 'top', alignment: 'start' },
        hAxis: {
            title: 'Time'
        },
        vAxis: {
            title: 'Value'
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

    chart.draw(data, options);

    // The second chart and the second timeseries
    // ------------------------------------
    // ------------------------------------
    // ------------------------------------

    var data1 = new google.visualization.DataTable();
    data1.addColumn('number', 'X');
    data1.addColumn('number', 'Timeseries_2');
    valRow = [];
    valArr = aMultiTimeseriesArr[1][1];
    xIndex = 0;

    valArr.forEach(val => {
        var arr = []
        arr.push(xIndex++);
        arr.push(val);
        valRow.push(arr);
    });

    data1.addRows(valRow);

    var chart1 = new google.visualization.LineChart(document.getElementById('chart_div1'));

    chart1.draw(data1, options);

    multiCharts();
}

function lineChart() {
    Highcharts.getJSON(
        'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
        function (data) {

            Highcharts.chart('container', {
                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: 'USD to EUR exchange rate over time'
                },
                subtitle: {
                    text: document.ontouchstart === undefined ?
                        'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
                },
                xAxis: {
                    //   type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: 'Exchange rate'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    area: {
                        fillColor: {
                            linearGradient: {
                                x1: 0,
                                y1: 0,
                                x2: 0,
                                y2: 1
                            },
                            stops: [
                                [0, Highcharts.getOptions().colors[0]],
                                [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                            ]
                        },
                        marker: {
                            radius: 2
                        },
                        lineWidth: 1,
                        states: {
                            hover: {
                                lineWidth: 1
                            }
                        },
                        threshold: null
                    }
                },

                series: [{
                    type: 'area',
                    name: 'USD to EUR',
                    data: data
                }]
            });
        }
    );
}