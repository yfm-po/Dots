import React, { useEffect, useState } from "react";
import Dot from "./Dot";
import { Container, Row, Col } from "react-bootstrap";
import styles from "./GameBoard.module.css";

function GameBoard({ dots, onSelectDot, onExecute }) {
		const [prevPositions, setPrevPositions] = useState(null);

		useEffect(() => {
				setPrevPositions(dots);
		}, [dots]);

		return (
				<Container>
						<div className={styles["game-board"]}>
								{dots.map((row, rowIndex) => {
										return (
												<Row key={rowIndex} className={`${styles["row-spacing"]} justify-content-md-center`}>
														{row.map((dot, columnIndex) => {
																return (
																		<Col
																				key={columnIndex}
																				xs="auto"
																				className={`${styles["col-spacing"]} d-flex justify-content-center`}
																		>
																				<Dot
																						dot={dot}
																						prevDot={prevPositions ? prevPositions[rowIndex][columnIndex] : null}
																						onSelectDot={() => onSelectDot(rowIndex, columnIndex)}
																						onExecute={() => onExecute()}
																				/>
																		</Col>
																);
														})}
												</Row>
										);
								})}
						</div>
				</Container>
		);
}

export default GameBoard;