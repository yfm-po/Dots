import gsAxios from "./index"
import {formatDate} from "./utils";

// GET /score/{game}
export const fetchScores = game => gsAxios.get('/score/' + game);

// POST /score
export const addScore = (player, game, points) => gsAxios.post('/score', {
		player, game, points, playedAt: formatDate(new Date()),
});
