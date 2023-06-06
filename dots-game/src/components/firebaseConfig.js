import "firebase/auth";
import "firebase/analytics";
import { initializeApp } from "firebase/app";

const firebaseConfig = {
  apiKey: "AIzaSyAV_Jg4iqyVPQYT9jwo7NtSQ6SQB2ZAv0I",
  authDomain: "dots-gamestudio.firebaseapp.com",
  databaseURL:
    "https://dots-gamestudio-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "dots-gamestudio",
  storageBucket: "dots-gamestudio.appspot.com",
  messagingSenderId: "401322933110",
  appId: "1:401322933110:web:bb2e346659b3cfa4c6d857",
  measurementId: "G-T7E6LV1MMV",
};

export const app = initializeApp(firebaseConfig);
