document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/v1/elo-scores/random-pair')
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                console.error('Error fetching Elo scores:', data.error);
                return;
            }

            const game1 = data.game1;
            const game2 = data.game2;

            const container = document.querySelector('.game-container');
            container.innerHTML = `
                <div class="game">
                    <img src="${game1.imageUrl}" alt="${game1.name}">
                    <p>${game1.name}</p>
                </div>
                <div class="game">
                    <img src="${game2.imageUrl}" alt="${game2.name}">
                    <p>${game2.name}</p>
                </div>
            `;
        })
        .catch(error => console.error('Error fetching games:', error));
});