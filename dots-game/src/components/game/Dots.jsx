import {Button, Card, Container, Row, Col, Badge, ListGroup} from "react-bootstrap";
import React, { useEffect, useState } from "react";
import gameBoardService from "./_api/dotsGameBoardService.js";
import GameBoard from "./GameBoard";
import ResultModal from "./ResultModal";
import LevelSelectModal from "./LevelSelectModal";
import styles from "./Title.module.css";

function Dots({onSendScore}) {
		const [gameBoard, setGameBoard] = useState(null);
		const [scoreSent, setScoreSent] = useState(false);
		const [gameOver, setGameOver] = useState(false);
		const [won, setWon] = useState(false);
		const [gameStarted, setGameStarted] = useState(false);
		const [showStartButton, setShowStartButton] = useState(true);
		const [powerUpsCount, setPowerUpsCount] = useState({
				Bomb: 0,
				ExtraMoves: 0,
				ScoreMultiplier: 0
		});

		useEffect(() => {
				const fetchGameBoardData = async () => {
						const response = await gameBoardService.fetchGameBoard();
						setGameBoard(response.data);
						setPowerUpsCount(countPowerUps(response.data.powerUps));
				};
				fetchGameBoardData();
		}, []);

		useEffect(() => {
				if (gameBoard && ("WON".localeCompare(gameBoard?.gameState) === 0 || "LOST".localeCompare(gameBoard?.gameState) === 0) && !scoreSent) {
						if (onSendScore && !scoreSent) {
								onSendScore(gameBoard.score);
								setScoreSent(true);
								console.log("Score sent true");
						} else {
								console.log("Score not sent");
						}
				}
		}, [gameBoard]);

		useEffect(() => {
				if (gameBoard && ("WON".localeCompare(gameBoard?.gameState) === 0 || "LOST".localeCompare(gameBoard?.gameState) === 0)) {
						setGameOver(true);
						setWon("WON".localeCompare(gameBoard?.gameState) === 0);
				} else {
						setGameOver(false);
				}
		}, [gameBoard]);

		const handleBuyPowerUp = async (powerUpType) => {
				if ("PLAYING".localeCompare(gameBoard?.gameState) !== 0) {
						return;
				}

				try {
						const response = await gameBoardService.buyPowerUp(powerUpType);
						setGameBoard(response.data);
						setPowerUpsCount(countPowerUps(response.data.powerUps));
				} catch (error) {
						console.error('Error buying powerup:', error);
				}
		};

		const handleUsePowerUp = async (powerUpType) => {
				if ("PLAYING".localeCompare(gameBoard?.gameState) !== 0) {
						return;
				}

				try {
						const response = await gameBoardService.applyPowerUp(powerUpType, 1, 1);
						setGameBoard(response.data);
						setPowerUpsCount(countPowerUps(response.data.powerUps));
				} catch (error) {
						console.error('Error using powerup:', error);
				}
		}

		const handleDotAction = (rowIndex, columnIndex, serviceAction) => {
				if ("PLAYING".localeCompare(gameBoard?.gameState) !== 0) {
						return;
				}

				serviceAction(rowIndex, columnIndex).then((response) => {
						setGameBoard(response.data);
				});
		};

		const handleNewGame = (level) => {
				gameBoardService.newGame(level).then((response) => {
						setGameBoard(response.data);
						setPowerUpsCount(countPowerUps(response.data.powerUps));
						setScoreSent(false);
						console.log("score sent false");
						setGameStarted(true);
				});
		};

		const executeDots = async () => {
				if ("PLAYING".localeCompare(gameBoard?.gameState) !== 0) {
						return;
				}

				gameBoardService.executeDots().then((response) => {
						setGameBoard(response.data);
				});


				const audio = new Audio(`${process.env.PUBLIC_URL}/sound.mp3`);
				try {
						await audio.play();
				} catch (err) {
						console.error("Failed to play sound effect:", err);
				}
		};

		const countPowerUps = (powerUps) => {
				const counts = {
						Bomb: 0,
						ExtraMoves: 0,
						ScoreMultiplier: 0
				};

				powerUps.forEach((powerUp) => {
						if (counts.hasOwnProperty(powerUp.name)) {
								counts[powerUp.name]++;
						}
				});

				return counts;
		};

		return (
				<Container className="justify-content-md-center mt-4">
						{showStartButton && (
								<>
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														<h1
																className={`text-center light-text ${styles.floating}`}
																style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
														>
																Dots
														</h1>
												</Col>
										</Row>
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														<Card         className={`text-center ${styles.floating}`}
														              style={{ background: "linear-gradient(135deg, #FFD700, #FFC84A)", borderRadius: "10px" }}>
																<Card.Body style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																		<Card.Text>
																				Welcome to Dots! Your job is to connect
																				dots of the same color
																		</Card.Text>
																</Card.Body>
														</Card>
												</Col>
										</Row>
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														<h1         className={`text-center light-text ${styles.floating}`}
														            style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>How to Play? 🤔</h1>
												</Col>
										</Row>
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														<ListGroup className={`text-center shadow ${styles.floating}`}
														                   style={{ background: "linear-gradient(135deg, #C0C0C0, #E8E8E8)", borderRadius: "10px", textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																<ListGroup.Item className="text-center">Select dots of the same color by clicking on them 👆🖱️</ListGroup.Item>
																<ListGroup.Item className="text-center">Hit right click to execute the selected dot 🖱️👆</ListGroup.Item>
																<ListGroup.Item className="text-center">There are dots of 4 colors: <img src={`${process.env.PUBLIC_URL}/images/dots/red.png`} alt="red" width="20" height="20" /> <img src={`${process.env.PUBLIC_URL}/images/dots/green.png`} alt="green" width="20" height="20" /> <img src={`${process.env.PUBLIC_URL}/images/dots/blue.png`} alt="blue" width="20" height="20" /> <img src={`${process.env.PUBLIC_URL}/images/dots/yellow.png`} alt="yellow" width="20" height="20" /></ListGroup.Item>
																<ListGroup.Item className="text-center">Buy and use power-ups to help you reach your goals.</ListGroup.Item>
																<ListGroup.Item className="text-center">There are 3 types of power-ups: Bomb <img src={`${process.env.PUBLIC_URL}/images/dots/bomb.svg`} alt="bomb" width="20" height="20" />, Extra Moves <img src={`${process.env.PUBLIC_URL}/images/dots/ExtraMoves.svg`} alt="extra-moves" width="20" height="20" /> and Score Multiplier <img src={`${process.env.PUBLIC_URL}/images/dots/ScoreMultiplier.png`} alt="score-multiplier" width="20" height="20" />.</ListGroup.Item>
																<ListGroup.Item className="text-center">Feel free to compete and get into the Hall Of Fame! 👑👑👑</ListGroup.Item>
														</ListGroup>
												</Col>
										</Row>
										<Row className={`justify-content-md-center mt-4 text-center`}>
												<Col md="auto">
														<Button variant="primary" onClick={() => {
																setShowStartButton(false);
																setGameOver(false);
																setGameStarted(false);
														}}>
																Start Game
														</Button>
												</Col>
										</Row>
								</>
						)}
						{!showStartButton && gameBoard && gameStarted && (
						<Row className="justify-content-md-center">
								<Col md="auto">
										{gameBoard && gameStarted && (
												<Row className="justify-content-md-center">
														<Col md="auto">
																<Card className="text-center shadow" style={{ background: "linear-gradient(135deg, #2e3f57, #5a7c9d)", borderRadius: "10px", marginTop: "-25px" }}>
																		<Card.Header className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>Game Stats</Card.Header>
																		<Card.Body className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																				<Card.Title>
																						Points:{" "}
																						<Badge bg="success">
																								{gameBoard?.score < 0 ? 0 : gameBoard?.score}
																						</Badge>{" "}
																						Moves:{" "}
																						<Badge bg="warning">{gameBoard?.remainingMoves}</Badge>
																				</Card.Title>
																				<Card.Text>
																						<>
																								{gameBoard?.executedRedDots < gameBoard?.neededRedDotsToBeExecuted && (
																										<>
																												<img src={`${process.env.PUBLIC_URL}/images/dots/red.png`} alt="red" width="30" height="30" />
																												<Badge bg="secondary">
																														{gameBoard?.executedRedDots}/{gameBoard?.neededRedDotsToBeExecuted}
																												</Badge>
																										</>
																								)}
																								{" "}
																								{gameBoard?.executedGreenDots < gameBoard?.neededGreenDotsToBeExecuted && (
																										<>
																												<img src={`${process.env.PUBLIC_URL}/images/dots/green.png`} alt="green" width="30" height="30" />
																												<Badge bg="secondary">
																														{gameBoard?.executedGreenDots}/{gameBoard?.neededGreenDotsToBeExecuted}
																												</Badge>
																										</>
																								)}
																								{" "}
																								{gameBoard?.executedBlueDots < gameBoard?.neededBlueDotsToBeExecuted && (
																										<>
																												<img src={`${process.env.PUBLIC_URL}/images/dots/blue.png`} alt="blue" width="30" height="30" />
																												<Badge bg="secondary">
																														{gameBoard?.executedBlueDots}/{gameBoard?.neededBlueDotsToBeExecuted}
																												</Badge>
																										</>
																								)}
																								{" "}
																								{gameBoard?.executedYellowDots < gameBoard?.neededYellowDotsToBeExecuted && (
																										<>
																												<img src={`${process.env.PUBLIC_URL}/images/dots/yellow.png`} alt="yellow" width="30" height="30" />
																												<Badge bg="secondary">
																														{gameBoard?.executedYellowDots}/{gameBoard?.neededYellowDotsToBeExecuted}
																												</Badge>
																										</>
																								)}
																						</>
																				</Card.Text>
																		</Card.Body>
																</Card>
														</Col>
												</Row>
										)}
												<GameBoard
														dots={gameBoard?.dots}
														onSelectDot={(rowIndex, columnIndex) =>
																handleDotAction(rowIndex, columnIndex, gameBoardService.selectDot)
														}
														onExecute={() => executeDots()}
												/>
								</Col>
								<Col md="auto">
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														<Card className="text-center shadow" style={{ background: "linear-gradient(135deg, #2e3f57, #5a7c9d)", borderRadius: "10px" }}>
																<Card.Header className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>PowerUp Shop</Card.Header>
																<Card.Body className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																		<Card.Title>
																				<img src={`${process.env.PUBLIC_URL}/images/dots/bomb.svg`} alt="bomb" width="30" height="30" />
																				{" "}
																				<Badge bg="danger">
																						{gameBoard?.powerUpShop?.availableBombs?.length}
																				</Badge>{" "}
																				<Button variant="success" onClick={() => handleBuyPowerUp("Bomb")} disabled={gameBoard?.gameState !== "PLAYING" || gameBoard?.powerUpShop?.bombPrice > gameBoard?.score}>
																						Buy
																				</Button>
																				{" "}
																		</Card.Title>
																		<Card.Title>
																				<img src={`${process.env.PUBLIC_URL}/images/dots/ExtraMoves.svg`} alt="extraMove" width="30" height="30" />{" "}
																				<Badge bg="warning">
																						{gameBoard?.powerUpShop?.availableExtraMoves?.length}
																				</Badge>{" "}
																				<Button variant="success" onClick={() => handleBuyPowerUp("ExtraMoves")} disabled={gameBoard?.gameState !== "PLAYING" || gameBoard?.powerUpShop?.extraMovesPrice > gameBoard?.score}>
																						Buy
																				</Button>
																				{" "}
																		</Card.Title>
																		<Card.Title>
																				<img src={`${process.env.PUBLIC_URL}/images/dots/ScoreMultiplier.png`} alt="scoreMultiply" width="30" height="30" />{" "}
																				<Badge bg="info">
																						{gameBoard?.powerUpShop?.availableScoreMultipliers?.length}
																				</Badge>{" "}
																				<Button variant="success" onClick={() => handleBuyPowerUp("ScoreMultiplier")} disabled={gameBoard?.gameState !== "PLAYING" || gameBoard?.powerUpShop?.scoreMultiplierPrice > gameBoard?.score}>
																						Buy
																				</Button>
																				{" "}
																		</Card.Title>
																</Card.Body>
														</Card>
												</Col>
										</Row>
										<Row className="justify-content-md-center mt-4">
												<Col md="auto">
														{powerUpsCount.Bomb > 0 || powerUpsCount.ExtraMoves > 0 || powerUpsCount.ScoreMultiplier > 0 ? (
																<Card className="text-center shadow" style={{ background: "linear-gradient(135deg, #2e3f57, #5a7c9d)", borderRadius: "10px" }}>
																		<Card.Header className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>Your PowerUps</Card.Header>
																		<Card.Body className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																				<Card.Title>
																						{powerUpsCount.Bomb > 0 && (
																								<Button variant="warning" onClick={() => handleUsePowerUp("Bomb")} disabled={gameBoard?.gameState !== "PLAYING" || powerUpsCount.Bomb === 0} style={{ marginBottom: '10px' }}>
																										<img src={`${process.env.PUBLIC_URL}/images/dots/bomb.svg`} alt="bomb" width="30" height="30" /> {powerUpsCount.Bomb}
																								</Button>
																						)}
																						<br />
																						{powerUpsCount.ExtraMoves > 0 && (
																								<Button variant="warning" onClick={() => handleUsePowerUp("ExtraMoves")} disabled={gameBoard?.gameState !== "PLAYING" || powerUpsCount.ExtraMoves === 0} style={{ marginBottom: '10px' }}>
																										<img src={`${process.env.PUBLIC_URL}/images/dots/ExtraMoves.svg`} alt="extraMove" width="30" height="30" /> {powerUpsCount.ExtraMoves}
																								</Button>
																						)}
																						<br />
																						{powerUpsCount.ScoreMultiplier > 0 && (
																								<Button variant="warning" onClick={() => handleUsePowerUp("ScoreMultiplier")} disabled={gameBoard?.gameState !== "PLAYING" || powerUpsCount.ScoreMultiplier === 0} style={{ marginBottom: '10px' }}>
																										<img src={`${process.env.PUBLIC_URL}/images/dots/ScoreMultiplier.png`} alt="scoreMultiply" width="30" height="30" /> {powerUpsCount.ScoreMultiplier}
																								</Button>
																						)}
																				</Card.Title>
																		</Card.Body>
																</Card>
														) : (
																<Card className="text-center shadow" style={{ background: "linear-gradient(135deg, #2e3f57, #5a7c9d)", borderRadius: "10px" }}>
																		<Card.Header className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>Your PowerUps</Card.Header>
																		<Card.Body className="light-text" style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.5)' }}>
																		</Card.Body>
																</Card>
														)}
												</Col>
										</Row>
								</Col>
						</Row>
								)}
						<ResultModal
								show={gameOver && !showStartButton}
								won={won}
								onHide={() => {
										setGameOver(false);
										setShowStartButton(true);
								}}
								onNewGame={() => {
										setGameOver(false);
										setGameStarted(false);
								}}
								resultScore={gameBoard?.score}
						/>
						<LevelSelectModal
								show={!gameStarted && !showStartButton}
								onHide={() => {
										setShowStartButton(true);
										setGameOver(false);
								}}
								onLevelSelect={handleNewGame}
						/>
				</Container>
		);
}

export default Dots;