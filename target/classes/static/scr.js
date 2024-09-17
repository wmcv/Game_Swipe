
/*
let myChart = null;


// Function to initialize or update the chart
function initializeOrUpdateChart(places, eloScores) {
    if (myChart) {
        // Destroy the existing chart before creating a new one
        myChart.destroy();
    }


document.addEventListener('DOMContentLoaded', () => {
    // Initialize the chart when the DOM is fully loaded
    const ctx = document.getElementById('myChart').getContext('2d');
    myChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: [0,1,2,3],
          datasets: [
            {
              label: "Elo Score Over Time",
              data: [3, 1, 2, 4],
              backgroundColor: "rgb(75, 192, 192, 0.2)",
              borderColor: "rgb(75, 192, 192, 1)",
              borderWidth: 1,
              fill: false,
            },
          ],
        },
        options: {
          scales: {
            x: {
              type: "linear",
              linear: {
                unit: "round",
              },
            },
            y: {
              beginAtZero: false,
            },
          },
        },
    });
});
}

*/

/*
var myChart = document.getElementById('eloChart'),
    ctx = myChart.getContext('2d'),
    startingData = {
        type: "line",
        data: {
          labels: [0,1,2,3],
          datasets: [
            {
              label: "Elo Score Over Time",
              data: [3, 1, 2, 4],
              backgroundColor: "rgb(75, 192, 192, 0.2)",
              borderColor: "rgb(75, 192, 192, 1)",
              borderWidth: 1,
              fill: false,
            },
          ],
        },
        options: {
          scales: {
            x: {
              type: "linear",
              linear: {
                unit: "round",
              },
            },
            y: {
              beginAtZero: false,
            },
          },
        }
    };

*/











fetch('http://localhost:8080/api/v1/game')
  .then(response => response.json())
  .then(data => {
    const select = document.getElementById('gameSelect');
    data.forEach(game => {
      const option = document.createElement('option');
      option.value = game.id;
      option.text = game.name;
      select.add(option);
    });
  });

  

  document.getElementById('gameSelect').addEventListener('change', function () {
    const gameId = this.value;
    fetch(`http://localhost:8080/api/v1/eloscore/allData/${gameId}`)
      .then(response => response.json())
      .then(data => {
        const eloScores = [];
        const places = [];

        Object.keys(data).forEach(key => {
          eloScores.push(data[key].elo_score);
          places.push(data[key].place);
        });

        updateChart(eloScores, places);
      });
  });


function updateChart(eloScores, places)
{
    


    if (!window.myChart) {
        console.error('Chart not initialized.');
        return;
    }

    // Check if the datasets array is defined and has at least one dataset
    //if (window.myChart.data.datasets.length === 0) {
     //   console.error('No datasets found.');
      //  return;
    //}
    //console.log("p "+places);
    //console.log("d "+eloScores);
    
    if (window.myChart.data && window.myChart.data.datasets && window.myChart.data.datasets.length > 0) {
    window.myChart.data.labels = places;
    //window.myChart.data.datasets[0].data = eloScores.map((value, index) => ({ x: labels[index], y: value }));
    window.myChart.data.datasets[0].data = eloScores;
    

    try {
        window.myChart.update(); // Refresh the chart with new data
    } catch (error) {
        console.error('Error updating chart:', error);
    }
} else {
    console.error('Chart data or datasets are undefined.');
}


}





// Initialize Chart.js (assuming you already have a chart initialized somewhere)
/*
const ctx = document.getElementById('eloChart').getContext('2d');
const chart = new Chart(ctx, {
    type: 'line', // Line chart for Elo scores over time
    data: {
        labels: [], // places will be populated here
        datasets: [{
            label: 'Elo Score Over Time',
            data: [], // Elo scores will be populated here
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
            fill: false
        }]
    },
    options: {
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'minute'
                },
                title: {
                    display: true,
                    text: 'Timestamp'
                }
            },
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: 'Elo Score'
                }
            }
        }
    }
});

*/



