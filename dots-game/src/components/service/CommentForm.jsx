import React from "react";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { useForm } from "react-hook-form";

function CommentForm({ onSendComment }) {
  const {
    register,
    handleSubmit,
    formState: { errors, isValid },
    reset,
  } = useForm({ mode: "onChange" });

  const onSubmit = (data) => {
    onSendComment(data.comment);
    reset();
  };

  return (
    <Container>
      <Row>
        <Col xs={12} sm={10} md={8} lg={6} className="mx-auto">
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Control
                autoComplete="off"
                type="text"
                {...register("comment", {
                  minLength: {
                    value: 4,
                    message: "Comment MUST be at least 4 characters long",
                  },
                  maxLength: {
                    value: 100,
                    message: "Your comment is too LONG!",
                  },
                  required: {
                    value: true,
                    message: "The field is empty",
                  },
                })}
                placeholder="Leave a comment"
                isInvalid={errors.comment}
              />
              <Form.Control.Feedback type="invalid" className="text-right">
                {errors.comment?.message}
              </Form.Control.Feedback>
            </Form.Group>

            <div className="d-flex justify-content-center">
              <Button type="submit" disabled={!isValid}>
                Send
              </Button>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  );
}

export default CommentForm;
