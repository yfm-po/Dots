import React from "react";
import { Button, Tooltip, OverlayTrigger, Modal } from "react-bootstrap";
import "./LevelSelectModal.css";

function LevelSelectModal({ show, onHide, onLevelSelect }) {

		const difficultyDescriptions = {
				"Newbie": "Great for beginners. Get a taste of the game.",
				"Easy": "A little more challenging, but still quite manageable.",
				"Medium": "For players who want a moderate challenge.",
				"Hard": "Things are getting tough. Be prepared for a challenge.",
				"Expert": "Only for the brave. Expect the unexpected.",
		};

		return (
				<Modal
						show={show}
						onHide={onHide}
						dialogClassName="custom-modal"
						contentClassName="custom-modal-content"
						centered
				>
						<Modal.Header className="custom-modal-header text-center" closeButton>
								<Modal.Title className="light-text text-center modal-title-centered">Select Difficulty Level</Modal.Title>
						</Modal.Header>
						<Modal.Body className="custom-modal-body">
								<div className="d-flex justify-content-center flex-wrap">
										{Object.entries(difficultyDescriptions).map(([levelString, description]) => (
												<OverlayTrigger
														key={levelString}
														placement="top"
														overlay={
																<Tooltip id={`tooltip-${levelString}`} className="custom-tooltip">
																		{description}
																</Tooltip>
														}
												>
														<Button
																className="m-1 btn-custom"
																variant="primary"
																onClick={() => {
																		let level = 0;
																		switch (levelString) {
																				case "Newbie":
																						level = 1;
																						break;
																				case "Easy":
																						level = 2;
																						break;
																				case "Medium":
																						level = 3;
																						break;
																				case "Hard":
																						level = 4;
																						break;
																				case "Expert":
																						level = 5;
																						break;
																		}
																		onLevelSelect(level);
																}}
														>
																{levelString}
														</Button>
												</OverlayTrigger>
										))}
								</div>
						</Modal.Body>
				</Modal>
		);
}

export default LevelSelectModal;