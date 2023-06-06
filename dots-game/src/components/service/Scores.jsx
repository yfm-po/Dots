import React from "react";
import { Card, Container, Row, Col } from "react-bootstrap";
import styles from "./Scores.module.css";
import "../game/Title.module.css";

const Podium = ({ scores }) => {
  return (
    <Row>
      {scores[1] && (
        <Col xs={6} md={{ span: 3, offset: 3 }}>
          <Card
            className={`shadow-sm p-3 mb-4 text-center ${styles.cardHoverEffect}`}
            style={{
              background: "linear-gradient(135deg, #C0C0C0, #E8E8E8)",
              borderRadius: "10px",
            }}
          >
            <Card.Body className="text-center light-text">
              <Card.Title className="card-title-overflow">
                🥈 {scores[1].player}
              </Card.Title>
              <Card.Text>{scores[1].points} points</Card.Text>
              <Card.Text>
                {new Date(scores[1].playedAt).toLocaleString()}
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      )}
      {scores[2] && (
        <Col xs={6} md={3}>
          <Card
            className={`shadow-sm p-3 mb-4 text-center ${styles.cardHoverEffect}`}
            style={{
              background: "linear-gradient(135deg, #CD7F32, #E8AA68)",
              borderRadius: "10px",
            }}
          >
            <Card.Body className="text-center light-text">
              <Card.Title className="card-title-overflow">
                🥉 {scores[2].player}
              </Card.Title>
              <Card.Text>{scores[2].points} points</Card.Text>
              <Card.Text>
                {new Date(scores[2].playedAt).toLocaleString()}
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      )}
    </Row>
  );
};

const Scores = ({ scores }) => {
  return (
    <Container>
      <Row>
        <Col>
          <h1 className="text-center mt-4 mb-4 light-text floating">
            {" "}
            Hall Of Fame 😎
          </h1>
          {scores[0] && (
            <Row>
              <Col xs={12} md={{ span: 4, offset: 4 }}>
                <Card
                  className={`shadow-sm p-3 mb-4 text-center ${styles.cardHoverEffect}`}
                  style={{
                    background: "linear-gradient(135deg, #FFD700, #FFC84A)",
                    borderRadius: "10px",
                  }}
                >
                  <Card.Body
                    className="text-center light-text"
                    style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                  >
                    <Card.Title className="card-title-overflow">
                      🥇 {scores[0].player}
                    </Card.Title>
                    <Card.Text>{scores[0].points} points</Card.Text>
                    <Card.Text>
                      {new Date(scores[0].playedAt).toLocaleString()}
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          )}
          {scores.length >= 2 && <Podium scores={scores} />}
          <Row>
            {scores.slice(3, 9).map((score, index) => (
              <Col
                xs={12}
                sm={6}
                md={4}
                key={`score-` + score.ident}
                className="mb-4"
              >
                <Card
                  className={`shadow-sm p-3 mb-4 text-center ${styles.cardHoverEffect}`}
                  style={{
                    background: "linear-gradient(135deg, #2e3f57, #5a7c9d)",
                    borderRadius: "10px",
                  }}
                >
                  <Card.Body
                    className="text-center light-text"
                    style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                  >
                    <Card.Title className="card-title-overflow">
                      {score.player}
                    </Card.Title>
                    <Card.Text>{score.points} points</Card.Text>
                    <Card.Text>
                      {new Date(score.playedAt).toLocaleString()}
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            ))}
            {scores[9] && (
              <Row className="justify-content-center mb-4">
                <Col xs={12} sm={6} md={4} key={`score-` + scores[9].ident}>
                  <Card
                    className={`shadow-sm p-3 mb-4 text-center ${styles.cardHoverEffect}`}
                    style={{
                      background: "linear-gradient(135deg, #2e3f57, #5a7c9d)",
                      borderRadius: "10px",
                    }}
                  >
                    <Card.Body
                      className="text-center light-text"
                      style={{ textShadow: "1px 1px 2px rgba(0, 0, 0, 0.5)" }}
                    >
                      <Card.Title className="card-title-overflow">
                        {scores[9].player}
                      </Card.Title>
                      <Card.Text>{scores[9].points} points</Card.Text>
                      <Card.Text>
                        {new Date(scores[9].playedAt).toLocaleString()}
                      </Card.Text>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            )}
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default Scores;
