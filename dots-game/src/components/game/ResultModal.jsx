import React from "react";
import { Modal, Button } from "react-bootstrap";
import "./ResultModal.css";

const ResultModal = ({ show, won, onHide, onNewGame, resultScore }) => {
	return (
		<Modal
			show={show}
			onHide={onHide}
			dialogClassName="custom-modal"
			contentClassName="custom-modal-content"
			centered
		>
			<Modal.Header className="custom-modal-header text-center" closeButton>
				<Modal.Title className="modal-title-centered">
					{won ? "Congratulations!" : "Better Luck Next Time!"}</Modal.Title>
			</Modal.Header>
			<Modal.Body className="custom-modal-body text-center">
				{won
					? `You won the game with a score of ${resultScore}! 🎉`
					: `You lost the game with a score of ${resultScore}. 😞`}
			</Modal.Body>
			<Modal.Footer className="custom-modal-footer">
				<Button variant="primary" onClick={onNewGame} className="btn-custom">
					New Game
				</Button>
			</Modal.Footer>
		</Modal>
	);
};

export default ResultModal;