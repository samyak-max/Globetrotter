import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@/components/ui/select";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription, DialogClose } from "@/components/ui/dialog";
import { toast } from "sonner";
import { Input } from "@/components/ui/input";

interface CityData {
  country: string;
  city: string;
  fun_facts: string[];
  trivia: string[];
  clues: string[];
}

export default function GamePage() {
  const location = useLocation();
  const navigate = useNavigate();
  const selectedCities = location.state?.selectedCities || [];
  const [cityQueue, setCityQueue] = useState<string[]>([]);
  const [currentCity, setCurrentCity] = useState<string | null>(null);
  const [clues, setClues] = useState<string[]>([]);
  const [currentClue, setCurrentClue] = useState<string | null>(null);
  const [options, setOptions] = useState<string[]>([]);
  const [answer, setAnswer] = useState<string>("");
  const [correctAnswers, setCorrectAnswers] = useState<number>(0);
  const [gameOver, setGameOver] = useState<boolean>(false);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [currentFunFacts, setCurrentFunFacts] = useState<string[]>([]);
  const [currentTrivia, setCurrentTrivia] = useState<string[]>([]);
  const [user, setUser] = useState<string>("");
  const [showUsernamePopup, setShowUsernamePopup] = useState<boolean>(false);

  useEffect(() => {
    if (selectedCities.length === 0) {
      navigate("/");
      return;
    }

    if (cityQueue.length === 0) {
      const shuffledCities = [...selectedCities].sort(() => Math.random() - 0.5);
      setCityQueue(shuffledCities);
    }
  }, [selectedCities, navigate, cityQueue]);

  useEffect(() => {
    if (cityQueue.length === 0) return;

    const nextCity = cityQueue[0];
    fetch(`data/city-data/fetch?city=${nextCity}`)
      .then((res) => res.json())
      .then((data: CityData) => {
        setCurrentCity(data.city);
        setClues(data.clues.sort(() => Math.random() - 0.5));
        setOptions([...cityQueue]);
        setCurrentFunFacts(data.fun_facts);
        setCurrentTrivia(data.trivia);
      })
      .catch((error) => console.error("Error fetching city data:", error));
  }, [cityQueue]);

  useEffect(() => {
    if (clues.length > 0) {
      setCurrentClue(clues[0]);
    }
  }, [clues]);

  const handleSubmit = () => {
    if (answer === currentCity) {
      setCorrectAnswers((prev) => prev + 1);
      setShowModal(true);
    } else {
      toast.error("😢 Wrong Answer! Moving to the next question...");
      moveToNextCity();
    }
  };

  const moveToNextCity = () => {
    const newQueue = cityQueue.slice(1);
    setCityQueue(newQueue);
    if (newQueue.length === 0) {
      setGameOver(true);
    } else {
      setClues([]);
      setCurrentClue(null);
    }
  };

  const handleChallengeFriend = () => {
    setShowUsernamePopup(true);
  };
  
  const handleChallengeFriendSubmit = async () => {
    setShowUsernamePopup(false);
    const discriminator = Math.floor(1000 + Math.random() * 9000);
    const uniqueUsername = `${user}-${discriminator}`;
    try {
        const response = await fetch(`/user/user-invite?username=${uniqueUsername}&highScore=${correctAnswers}`, {
          method: "POST",
        });
        if (response.ok) {
          const inviteText = `🔥 I scored ${correctAnswers} in Globetrotter! Can you beat me? 🌍 Play now: https://globetrotter-game.com/invite/${uniqueUsername}`;
          const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(inviteText)}`;
          window.open(whatsappUrl, "_blank");
        } else {
          toast.error("Failed to save username. Try again!");
        }
      } catch (error) {
        toast.error("Network error. Try again!");
      }
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-6">
      <h1 className="text-3xl font-bold mb-4">{gameOver?"Well Played! ":"Guess the City"}</h1>

      {gameOver ? (
        <>
            <p className="text-lg mb-2">Correct Answers: {correctAnswers}</p>
            <p className="text-xl font-bold text-green-600">Game Over! 🏁</p>
            <Button onClick={handleChallengeFriend} className="mt-4 px-6 py-2 bg-blue-500 text-white">
            Challenge Your Friend 🔥
          </Button>
        </>
        ) : (
            <>
            <p className="text-lg mb-2">Correct Answers: {correctAnswers}</p>
          {currentClue && <p className="mb-4 text-lg">{currentClue}</p>}

          <Select onValueChange={(value) => setAnswer(value)}>
            <SelectTrigger className="w-[200px]">
              <SelectValue placeholder="Select a city" />
            </SelectTrigger>
            <SelectContent>
              {options.map((city) => (
                <SelectItem key={city} value={city}>{city}</SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Button onClick={handleSubmit} className="mt-4 px-6 py-2" disabled={!answer}>
            Submit
          </Button>
        </>
      )}
      {/* Username Popup */}
      <Dialog open={showUsernamePopup} onOpenChange={setShowUsernamePopup}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Enter Your Username</DialogTitle>
          </DialogHeader>
          <Input type="text" onChange={(e) => setUser(e.target.value)} placeholder="Enter username" />
          <DialogClose asChild>
            <Button onClick={handleChallengeFriendSubmit} className="mt-4">Challenge!</Button>
          </DialogClose>
        </DialogContent>
      </Dialog>
      {/* Popup for correct answer */}
      <Dialog open={showModal} onOpenChange={setShowModal}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>🎉 Correct! {currentCity}</DialogTitle>
            <DialogDescription>
              <div className="mt-2">
                <h3 className="font-semibold">Fun Facts:</h3>
                <ul className="list-disc ml-5">
                  {currentFunFacts.map((fact, index) => (
                    <li key={index}>{fact}</li>
                  ))}
                </ul>
              </div>
              <div className="mt-2">
                <h3 className="font-semibold">Trivia:</h3>
                <ul className="list-disc ml-5">
                  {currentTrivia.map((trivia, index) => (
                    <li key={index}>{trivia}</li>
                  ))}
                </ul>
              </div>
            </DialogDescription>
          </DialogHeader>
          <DialogClose asChild>
            <Button onClick={moveToNextCity} className="mt-4">Next Question</Button>
          </DialogClose>
        </DialogContent>
      </Dialog>
    </div>
  );
}
