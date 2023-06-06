import React from "react";
import { Container, Row, Col } from "react-bootstrap";

const Footer = () => {
  return (
    <footer className="footer">
      <Container>
        <Row>
          <Col className="text-center text-muted my-auto">
            © {new Date().getFullYear()} - Patrick Obrtal
          </Col>
        </Row>
      </Container>
    </footer>
  );
};

export default Footer;
