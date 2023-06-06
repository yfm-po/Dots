import gsAxios from "../../../_api/index";

const GAMEBOARD_URL = '/dots/gameboard';
const NEW_GAMEBOARD_URL = '/dots/gameboard/newGame';
const SELECT_DOT_URL = '/dots/gameboard/selectDot';
const EXECUTE_DOT_URL = '/dots/gameboard/executeDots';
const BUY_POWERUP_URL = '/dots/gameboard/buyPowerUp';
const APPLY_POWERUP_URL = '/dots/gameboard/applyPowerUp';

const fetchGameBoard = () => gsAxios.get(GAMEBOARD_URL);

const newGame = (level) => {
		return gsAxios.get(`${NEW_GAMEBOARD_URL}?level=${level}`);
}

const selectDot = (row, column) => {
		return gsAxios.get(`${SELECT_DOT_URL}?row=${row}&column=${column}`);
}

const executeDots = () => {
		return gsAxios.get(EXECUTE_DOT_URL);
}

const buyPowerUp = (powerUpName) => {
		return gsAxios.get(`${BUY_POWERUP_URL}?powerUpName=${powerUpName}`);
}

const applyPowerUp = (powerUpName, row, column) => {
		return gsAxios.get(`${APPLY_POWERUP_URL}?powerUpName=${powerUpName}&row=${row}&column=${column}`);
}

const gameBoardService = {
		fetchGameBoard,
		newGame,
		selectDot,
		executeDots,
		buyPowerUp,
		applyPowerUp,
};

export default gameBoardService;

