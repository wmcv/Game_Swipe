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

    // Function to animate the divs out and in
    function animateOutAndIn(winnerGame, loserGame, winnerDirection) {
        
        if (animating) return; // Prevent multiple clicks during animation
        animating = true;

        // Animate the winnerGame out (up) and loserGame out (down)
        winnerGame.classList.add('slide-up');
        loserGame.classList.add('slide-down');

        setTimeout(() => {
            // Teleport both off-screen
            winnerGame.classList.add('hidden');
            loserGame.classList.add('hidden');
            winnerGame.classList.add(winnerDirection === 'left' ? 'offscreen-left' : 'offscreen-right');
            loserGame.classList.add(winnerDirection === 'left' ? 'offscreen-right' : 'offscreen-left');

            // Remove slide classes after teleporting
            winnerGame.classList.remove('slide-up');
            loserGame.classList.remove('slide-down');
            // After teleporting off-screen, slide them back in after a short delay
            setTimeout(() => {
                winnerGame.classList.remove('hidden');
                loserGame.classList.remove('hidden');
                winnerGame.classList.remove('offscreen-left', 'offscreen-right');
                loserGame.classList.remove('offscreen-left', 'offscreen-right');

                // Update game content (images and titles)
                updateGameContent();

                // Re-enable clicks after reset
                resetGameDivs();
            }, 500); // Delay before sliding back in
        }, 1000); // Wait for 1 second before teleporting off-screen
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
    
    fetch('/api/v1/eloscore/random-pair')  // URL should match your API endpoint
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error("h   "+data.error);
                return;
            }
            // Update the HTML with the game data
            updateGameDisplay(data.game1, 'game1', 0);
            updateGameDisplay(data.game2, 'game2', 1);
        })
        .catch(error => console.error('Error fetching data:', error));
}

function updateGameDisplay(gameData, elementId, duck) {
    const gameElement = document.getElementById(elementId);
    if (gameData) {
        if (duck == 0)
        {
            //console.log(url1);
            gId1 = gameData.id;
            game1.querySelector('img').src = gameData.imageUrl;
            game1.querySelector('h2').textContent = gameData.name;
            //tit1 = gameData.name;
            //url1 = gameData.image_Url;
        }
        if (duck == 1)
        {
            gId2 = gameData.id;
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