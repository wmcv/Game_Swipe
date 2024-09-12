document.addEventListener('DOMContentLoaded', () => {
    // Fetch random games when the page is loaded
    fetchRandomGames();
});

function fetchRandomGames() {
    fetch('/api/random-pair')  // URL should match your API endpoint
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error(data.error);
                return;
            }

            // Update the HTML with the game data
            updateGameDisplay(data.game1, 'game1');
            updateGameDisplay(data.game2, 'game2');
        })
        .catch(error => console.error('Error fetching data:', error));
}

function updateGameDisplay(gameData, elementId) {
    const gameElement = document.getElementById(elementId);
    if (gameData) {
        gameElement.innerHTML = `
            <h2>${gameData.name}</h2>
            <img src="${gameData.imageUrl}" alt="${gameData.name}" />
        `;
    }
}