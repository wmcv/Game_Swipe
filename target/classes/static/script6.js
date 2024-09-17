gId1 = -1;
gId2 = -1;
tit1 = "";
tit2 = "";
url1 = "";
url2 = "";


document.addEventListener('DOMContentLoaded', () => {
    const game1 = document.getElementById('game1');
    const game2 = document.getElementById('game2');
    const leftButton = document.getElementById('left-button');
    const rightButton = document.getElementById('right-button');



    fetchRandomGames();
    let animating = false; // Flag to prevent multiple clicks during animation

    // Function to reset div positions and re-enable clicks
    function resetGameDivs() {
        game1.classList.remove('offscreen-left', 'offscreen-right', 'slide-up', 'slide-down');
        game2.classList.remove('offscreen-left', 'offscreen-right', 'slide-up', 'slide-down');
        animating = false; // Reset animation flag
        addClickEvents();  // Re-enable click events after reset
    }

    // Function to animate the divs in/out
    function animateOutAndIn(winnerGame, loserGame, winnerDirection) {
        const winnerId = winnerGame.dataset.gameId;
        const loserId = loserGame.dataset.gameId;
        if (animating) return; // Prevent multiple clicks during animation
        animating = true;

        winnerGame.classList.add('slide-up');
        loserGame.classList.add('slide-down');
      

        setTimeout(() => {
            winnerGame.classList.add('hidden');
            loserGame.classList.add('hidden');
            winnerGame.classList.add(winnerDirection === 'left' ? 'offscreen-left' : 'offscreen-right');
            loserGame.classList.add(winnerDirection === 'left' ? 'offscreen-right' : 'offscreen-left');

     
            winnerGame.classList.remove('slide-up');
            loserGame.classList.remove('slide-down');
            
            //elo_score code BELOW
            updateEloScore(winnerId, loserId)
            .then(() => {
            //elo_score code ABOVE
            setTimeout(() => {
                winnerGame.classList.remove('hidden');
                loserGame.classList.remove('hidden');
                updateGameContent();
                winnerGame.classList.remove('offscreen-left', 'offscreen-right');
                loserGame.classList.remove('offscreen-left', 'offscreen-right');
                winnerGame.classList.add('visible');
                loserGame.classList.add('visible');
               

                
                setTimeout(() => {
                    winnerGame.classList.remove('visible');
                    loserGame.classList.remove('visible');
                
                    
                    // Re-enable clicks after reset
                    resetGameDivs();


                }, 700); //slide back in speed
              }, 200); //translate in from offscreen
    });}, 700); //slide up/down offscren
    }
    
    function addClickEvents() {
        game1.addEventListener('click', handleClick1);
        game2.addEventListener('click', handleClick2);
    }

    // Simulate updating the image and title
    function updateGameContent() {
        fetchRandomGames();
        //game1.querySelector('img').src = url1;
        //game1.querySelector('h2').textContent = tit1;
        //game2.querySelector('img').src = url2;
        //game2.querySelector('h2').textContent = tit2;
    }

    // Function to add click events to the game divs
    function addClickEvents() {
        game1.querySelector('img').addEventListener('click', handleClick1);
        game2.querySelector('img').addEventListener('click', handleClick2);
    }

    function handleClick1() {
        if (!animating) {
            animateOutAndIn(game1, game2, 'left');
        }
    }

    function handleClick2() {
        if (!animating) {
            animateOutAndIn(game2, game1, 'right');
        }
    }

    // Initialize click events on page load
    addClickEvents();

    // Buttons for navigation
    leftButton.addEventListener('click', () => {
        window.location.href = 'stats.html'; // Redirect to "Statistics"
    });

    rightButton.addEventListener('click', () => {
        window.location.href = 'rank.html'; // Redirect to "Rankings"
    });
});



//document.addEventListener('DOMContentLoaded', () => {
    // Fetch random games when the page is loaded
//    fetchRandomGames();
//});
///random-pair



function fetchRandomGames() {
    
    fetch('/api/v1/eloscore/random-pair')
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("h   "+data.error);
                return;
            }
            if (data.game1.id !== data.game2.id)
            {
            updateGameDisplay(data.game1, 'game1', 0);
            updateGameDisplay(data.game2, 'game2', 1);
            }
            else
            {
                fetchRandomGames();
            }
        })
        .catch(error => console.error('Error fetching data:', error));
}

function updateGameDisplay(gameData, elementId, duck) {
    const gameElement = document.getElementById(elementId);
    if (gameData) {
        if (duck == 0)
        {
            //console.log(url1);
            gameElement.dataset.gameId = gameData.id;
            gId1 = gameData.id;
            game1.querySelector('img').src = gameData.imageUrl;
            game1.querySelector('h2').textContent = gameData.name;
            //tit1 = gameData.name;
            //url1 = gameData.image_Url;
        }
        if (duck == 1)
        {
            gId2 = gameData.id;
            gameElement.dataset.gameId = gameData.id;
            game2.querySelector('img').src = gameData.imageUrl;
            game2.querySelector('h2').textContent = gameData.name;
            //tit2 = gameData.name;
            //url2 = gameData.image_Url;
        }

        


        //gameElement.innerHTML = `
        //    <h2>${gameData.name}</h2>
        //    <img src="${gameData.imageUrl}" alt="${gameData.name}" />
        //`;
    }
}







function fetchEloScore(gameId) {
    return fetch(`/api/v1/eloscore/${gameId}`)
        .then(response => response.json())
        .then(data => data.elo_score)
        .catch(error => console.error('Error fetching Elo score:', error));
}

function updateGameContent() {
    // Assume that these IDs are dynamically selected
    const leftGameId = leftGame.dataset.gameId;
    const rightGameId = rightGame.dataset.gameId;

    // Fetch current Elo scores for both games
    Promise.all([fetchEloScore(leftGameId), fetchEloScore(rightGameId)])
        .then(([leftElo, rightElo]) => {
            // Update the DOM with the new game content and their Elo scores
            document.querySelector('#left-game-elo').textContent = `Elo: ${leftElo}`;
            document.querySelector('#right-game-elo').textContent = `Elo: ${rightElo}`;
        })
        .catch(error => console.error('Error updating game content:', error));
}

function updateEloScore(winnerId, loserId) {

    return fetch(path='api/v1/eloscore/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        //body: JSON.stringify({winnerId: winnerId, loserId: loserId })
        body: JSON.stringify({winnerId: 3, loserId: 4 })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text(); // Use text() to handle empty or non-JSON responses
    })
    .then(text => {
        try {
            const data = JSON.parse(text);
            console.log('Elo scores updated:', data);
        } catch (e) {
            console.error('Error parsing JSON:', e);
        }
    })
    .catch(error => console.error('Error updating Elo score:', error));
}
    
    
    
    
    
    /*
    .then(response => response.json())
    .then(data => console.log('Elo scores updated:', data))
    .catch(error => console.error('Error updating Elo score:', error));
}
    */