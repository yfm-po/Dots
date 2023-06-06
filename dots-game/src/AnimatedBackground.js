import React from "react";
import "./AnimatedBackground.css";

const AnimatedBackground = () => {
  const numberOfDots = 100;

  const generateDots = () => {
    let dots = [];
    for (let i = 0; i < numberOfDots; i++) {
      const size = Math.random() * 5 + 10;
      const duration = Math.random() * 3 + 2;
      const delay = Math.random() * duration;
      const left = Math.random() * 100;
      const color = `hsla(${Math.random() * 360}, 100%, 50%, 0.8)`;

      const style = {
        width: `${size}px`,
        height: `${size}px`,
        animationDuration: `${duration}s`,
        animationDelay: `${delay}s`,
        left: `${left}%`,
        backgroundColor: color,
      };

      dots.push(<div key={i} className="falling-dot" style={style} />);
    }
    return dots;
  };

  return (
    <div className="AnimatedBackground">
      <div className="falling-dots-container">{generateDots()}</div>
    </div>
  );
};

export default AnimatedBackground;
