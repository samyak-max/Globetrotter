import './App.css'
import LandingPage from './components/LandingPage.tsx'
import GamePage from './components/GamePage.tsx';
import { ThemeProvider } from "@/context/ThemeContext";
import { BrowserRouter as Router, Routes, Route, useParams, useNavigate } from 'react-router-dom';
import { Toaster } from "sonner";
import { useEffect } from 'react';
function App() {

  return (
    <>
    <Toaster position="bottom-right" richColors />
    <ThemeProvider>
      <Router>
        <Routes>
          <Route path="/" element={<LandingPage />} />
          <Route path="/game" element={<GamePage />} />
          <Route path="/invite/:username" element={<InviteRedirect />} />
        </Routes>
      </Router>
    </ThemeProvider>
    </>
  )
}

function InviteRedirect() {
  const { username } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    navigate(`/?invitedBy=${username}`);
  }, [username, navigate]);

  return null;
}

export default App
