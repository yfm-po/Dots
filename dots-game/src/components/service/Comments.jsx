import React from "react";
import { Carousel, Container } from "react-bootstrap";
import "./Comments.css";

const Comments = ({ comments }) => {
  return (
    <div>
      <h1 className="text-center mb-5 light-text">Latest Comments 💬</h1>

      <Container>
        <Carousel
          indicators={false}
          pause="hover"
          className="height: 400px custom-carousel"
        >
          {comments.map((comment) => (
            <Carousel.Item
              key={`comment-` + comment.ident}
              className="height-200px"
            >
              <div className="d-flex justify-content-center mb-4">
                <div
                  className="shadow-sm p-3 mb-4 text-center"
                  style={{
                    background: "linear-gradient(135deg, #2e3f57, #5a7c9d)",
                    borderRadius: "10px",
                  }}
                >
                  <h5 className="light-text">{comment.comment}</h5>
                  <p className="light-text">{comment.player}</p>
                  <p className="light-text">
                    {new Date(comment.commentedAt).toLocaleString()}
                  </p>
                </div>
              </div>
            </Carousel.Item>
          ))}
        </Carousel>
      </Container>
    </div>
  );
};

export default Comments;
