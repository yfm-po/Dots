import React from "react";
import { Card, Container, Row, Col } from "react-bootstrap";
import { FaStar } from "react-icons/fa";
import styles from "./Ratings.module.css";

const Ratings = ({ ratings, averageRating }) => {
  const lastIndex = ratings.length - 1;

  console.log("ratings:", ratings);
  console.log("averageRating:", averageRating);

  return (
    <div>
      <h1 className="text-center mt-4 mb-4 light-text">Latest Ratings 🌠</h1>

      <div style={{ textAlign: "center" }}>
        <h3 className="light-text">Average Rating</h3>
        {[...Array(5)].map((_, i) => {
          const ratingValue = i + 1;
          return (
            <FaStar
              key={i}
              color={ratingValue <= averageRating ? "#ffc107" : "#e4e5e9"}
              size="2em"
            />
          );
        })}
        <p className="light-text">{averageRating}/5 stars</p>
      </div>

      <Container>
        <Row>
          {ratings.slice(0, lastIndex).map((rating) => (
            <Col
              xs={12}
              sm={6}
              md={4}
              key={`rating-` + rating.ident}
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
                  <Card.Title>
                    {[...Array(5)].map((_, i) => {
                      const ratingValue = i + 1;
                      return (
                        <FaStar
                          key={i}
                          color={
                            ratingValue <= rating.rating ? "#ffc107" : "#e4e5e9"
                          }
                        />
                      );
                    })}
                  </Card.Title>
                  <Card.Text>{rating.player}</Card.Text>
                  <Card.Text>
                    {new Date(rating.ratedAt).toLocaleString()}
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
          {ratings[9] && (
            <Row className="justify-content-center mb-4">
              <Col xs={12} sm={6} md={4} key={`rating-` + ratings[9].ident}>
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
                    <Card.Title>
                      {[...Array(5)].map((_, i) => {
                        const ratingValue = i + 1;
                        return (
                          <FaStar
                            key={i}
                            color={
                              ratingValue <= ratings[lastIndex].rating
                                ? "#ffc107"
                                : "#e4e5e9"
                            }
                          />
                        );
                      })}
                    </Card.Title>
                    <Card.Text>{ratings[lastIndex].player}</Card.Text>
                    <Card.Text>
                      {new Date(ratings[lastIndex].ratedAt).toLocaleString()}
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          )}
        </Row>
      </Container>
    </div>
  );
};

export default Ratings;
