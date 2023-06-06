import React from "react";
import { useSpring, animated } from "react-spring";
import styles from "./Dot.module.css";

function Dot({ dot, prevDot, onSelectDot, onExecute }) {
		const yOffset = prevDot && prevDot.y - dot.y;

		const animation = useSpring({
				from: { transform: `translateY(${yOffset * 100}%)` },
				to: { transform: "translateY(0%)" },
				config: { duration: 500 },
		});

		let dotImage;

		if ("BLUE".localeCompare(dot?.color) === 0) {
				dotImage = dot.selected ? 'images/dots/blue_selected.png' : 'images/dots/blue.png';
		} else if ("RED".localeCompare(dot?.color) === 0) {
				dotImage = dot.selected ? 'images/dots/red_selected.png' : 'images/dots/red.png';
		} else if ("GREEN".localeCompare(dot?.color) === 0) {
				dotImage = dot.selected ? 'images/dots/green_selected.png' : 'images/dots/green.png';
		} else if ("YELLOW".localeCompare(dot?.color) === 0) {
				dotImage = dot.selected ? 'images/dots/yellow_selected.png' : 'images/dots/yellow.png';
		}

		const handleDotClicked = () => {
				if ("SELECTED".localeCompare(dot?.state) === 0) {
						return;
				}
				onSelectDot();
		};

		const handleDotRightClick = (event) => {
				event.preventDefault();
				onExecute();
		};

		return (
				<td>
      <span
		      className={styles.dotWrapper}
		      onClick={handleDotClicked}
		      onContextMenu={handleDotRightClick}
      >
        <animated.img
		        style={animation}
		        src={`${process.env.PUBLIC_URL}/${dotImage}`}
		        alt={dot?.color}
		        width="50"
        />
      </span>
				</td>
		);
}

export default Dot;