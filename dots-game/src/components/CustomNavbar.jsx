import React from "react";
import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import Logout from "./Logout";

function CustomNavbar({ user }) {
  return (
    <Navbar bg="dark" expand="lg" fixed="top">
      <Container fluid>
        <Navbar.Brand href="game">
          <img
            src={process.env.PUBLIC_URL + "/images/dots/dots_logo.png"}
            alt="logo"
            width="50"
            height="50"
          />
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbar-nav" />
        <Navbar.Collapse id="navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="game" className="nav-link-dark">
              Play
            </Nav.Link>
            <Nav.Link as={Link} to="score" className="nav-link-dark">
              Hall Of Fame
            </Nav.Link>
            <Nav.Link as={Link} to="opinions" className="nav-link-dark">
              Comments
            </Nav.Link>
            <Nav.Link as={Link} to="ratings" className="nav-link-dark">
              Ratings
            </Nav.Link>
          </Nav>
          {user ? (
            <Nav>
              {user.photoURL && (
                <img
                  src={user.photoURL}
                  alt="User profile"
                  width="40"
                  height="40"
                  className="rounded-circle me-2"
                />
              )}
              <NavDropdown
                title={
                  <span className="nav-dropdown-title-dark">
                    {user.displayName ? user.displayName : user.email}
                  </span>
                }
                id="user-dropdown"
              >
                <NavDropdown.Item as={Logout}></NavDropdown.Item>
              </NavDropdown>
            </Nav>
          ) : (
            <Nav>
              <Nav.Link as={Link} to="register" className="nav-link-dark">
                Register
              </Nav.Link>
              <Nav.Link as={Link} to="login" className="nav-link-dark">
                Login
              </Nav.Link>
            </Nav>
          )}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default CustomNavbar;
