import gsAxios from "./index"
import {formatDate} from "./utils";

// GET /comment/{game}
export const fetchComments = game => gsAxios.get('/comment/' + game);

// POST /comment
export const addComment = (player, game, comment) => gsAxios.post('/comment', {
		player, game, comment, commentedAt: formatDate(new Date()),
});
