import gsAxios from "./index"
import {formatDate} from "./utils";

export const fetchRatings = game => gsAxios.get('/rating/' + game);

export const fetchAverageRating = game => gsAxios.get('/rating/average/' + game);

export const setRating = (player, game, rating) => gsAxios.post('/rating', {
		player, game, rating, ratedAt: formatDate(new Date()),
});
