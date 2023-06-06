import React, { useState } from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { useForm } from "react-hook-form";
import { FaStar } from "react-icons/fa";

function RatingForm({ onSendRating }) {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(null);
  const { register, handleSubmit } = useForm();

  const onSubmit = () => {
    onSendRating(rating);
    setRating(0);
  };

  return (
    <Container>
      <Row>
        <Col xs={12} sm={10} md={8} lg={6} className="mx-auto">
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group controlId="ratingForm">
              <div className="rating-container">
                {[...Array(5)].map((_, i) => {
                  const ratingValue = i + 1;
                  return (
                    <label key={i}>
                      <input
                        className="star-radio"
                        type="radio"
                        name="rating"
                        {...register("rating")}
                        value={ratingValue}
                        checked={rating === ratingValue}
                        onClick={() => setRating(ratingValue)}
                      />
                      <FaStar
                        className="star"
                        color={
                          ratingValue <= (hover || rating)
                            ? "#ffc107"
                            : "#e4e5e9"
                        }
                        onMouseEnter={() => setHover(ratingValue)}
                        onMouseLeave={() => setHover(null)}
                      />
                    </label>
                  );
                })}
              </div>
            </Form.Group>
            <div className="submit-button-container text-center">
              <Button type="submit" disabled={!rating}>
                Submit Rating
              </Button>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  );
}

export default RatingForm;
