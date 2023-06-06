import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min';
import './App.css';
import React from 'react';
import {addScore, fetchScores} from "./_api/score.service";
import {fetchAverageRating, fetchRatings, setRating} from "./_api/rating.service";
import {addComment, fetchComments} from "./_api/comment.service";
import {useEffect, useState} from "react";
import Comments from "./components/service/Comments";
import Scores from "./components/service/Scores";
import Ratings from "./components/service/Ratings";
import CommentForm from "./components/service/CommentForm";
import RatingForm from "./components/service/RatingForm";
import Dots from "./components/game/Dots";
import { Route, Routes } from 'react-router-dom';
import LoginPage from "./components/LoginPage";
import RegisterPage from './components/RegisterPage';
import { getAuth, onAuthStateChanged } from 'firebase/auth';
import Footer from "./components/Footer";
import CustomNavbar from "./components/CustomNavbar";
import './AnimatedBackground.css';
import AnimatedBackground from './AnimatedBackground';


function App() {
    const selectedGame = 'dots';
    const [comments, setComments] = useState([]);
    const [scores, setScores] = useState([]);
    const [ratings, setRatings] = useState([]);
    const [averageRating, setAverageRating] = useState(0);
    const [user, setUser] = useState(null);

    const auth = getAuth();

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (user) => {
            if (user) {
                setUser(user);
            } else {
                setUser(null);
            }
        });

        return () => {
            unsubscribe();
        };
    }, []);

    const fetchData = () => {
        fetchComments(selectedGame).then((response) => {
            setComments(response.data);
        });
        fetchScores(selectedGame).then((response) => {
            setScores(response.data);
        });
        fetchRatings(selectedGame).then((response) => {
            setRatings(response.data);
        });
        fetchAverageRating(selectedGame).then((response) => {
            setAverageRating(response.data);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleSendComment = comment => {
        if (user.displayName && !user.isAnonymous) {
            addComment(user.displayName, selectedGame, comment).then(() => {
                fetchData();
            });
        } else if (user && !user.isAnonymous) {
            addComment(user.email, selectedGame, comment).then(() => {
                fetchData();
            });
        }
    };

    const handleSendRating = rating => {
        if (user.displayName && !user.isAnonymous) {
            setRating(user.displayName, selectedGame, rating).then(() => {
                fetchData();
            });
        } else if (user && !user.isAnonymous) {
            setRating(user.email, selectedGame, rating).then(() => {
                fetchData();
            });
        }
    };

    const handleSendScore = score => {
        if (user.displayName && !user.isAnonymous) {
            addScore(user.displayName, selectedGame, score).then(() => {
                fetchData();
            });
        } else if (user && !user.isAnonymous) {
            addScore(user.email, selectedGame, score).then(() => {
                fetchData();
            });
        }
    };

    return (
        <div className="App mb-5">
            <AnimatedBackground />
            <CustomNavbar user={user} />

            <div className="container index-container main-content">
                <Routes>
                    <Route path={"/"} element={ <LoginPage /> } />
                    <Route
                        path={"score"}
                        element={
                                <React.Fragment>
                                    <Scores scores={scores} />
                                </React.Fragment>
                        }
                    />
                    <Route
                        path={"opinions"}
                        element={
                                <React.Fragment>
                                    <Comments comments={comments} />
                                    {user ? (
                                        user.isAnonymous ? (
                                            <p className="text-center light-text">You must be logged in with a non-anonymous account to leave a comment.</p>
                                        ) : (
                                            <>
                                                <CommentForm
                                                    player={user.displayName ? user.displayName : user.email}
                                                    game={selectedGame}
                                                    onSendComment={handleSendComment}
                                                />
                                            </>
                                        )
                                    ) : (
                                        <p className="text-center light-text">You must be logged in to leave a comment.</p>
                                    )}
                                </React.Fragment>
                        }
                    />
                    <Route
                        path={"ratings"}
                        element={
                                <React.Fragment>
                                    <Ratings ratings={ratings} averageRating={averageRating}/>
                                    {user ? (
                                        user.isAnonymous ? (
                                            <p className="text-center light-text">You must be logged in with a non-anonymous account to leave a rating.</p>
                                        ) : (
                                            <>
                                                <h2 className="text-center light-text">Leave a rating</h2>
                                                <RatingForm onSendRating={handleSendRating} />
                                            </>
                                        )
                                    ) : (
                                        <p className="text-center light-text">You must be logged in to leave a rating.</p>
                                    )}
                                </React.Fragment>
                        }
                    />
                    <Route
                        path={"game"}
                        element={
                                <Dots player={user && !user.isAnonymous ? user.email : 'Guest'}
                                      game={selectedGame}
                                      onSendScore={user && !user.isAnonymous ? handleSendScore : null} />
                        }
                    />
                    <Route path={"login"} element={<LoginPage />} />
                    <Route path={"register"} element={<RegisterPage />} />
                </Routes>
            </div>
            <Footer />
        </div>
    );
}


export default App;
