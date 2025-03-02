import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Toggle } from "@/components/ui/toggle";
import { Switch } from "@/components/ui/switch";
import { useTheme } from "@/context/ThemeContext";
import { useNavigate, useLocation } from "react-router-dom";
import globeGif from "@/assets/spinning-the-globe-rick-sanchez.gif";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogClose } from "@/components/ui/dialog";

interface City {
  country: string;
  city: string;
}

export default function LandingPage() {
  const [cities, setCities] = useState<City[]>([]);
  const [selectedCities, setSelectedCities] = useState<string[]>([]);
  const { darkMode, toggleDarkMode } = useTheme();
  const navigate = useNavigate();
  const location = useLocation();
  const [inviteMessage, setInviteMessage] = useState<string>("");
  const [isDialogOpen, setIsDialogOpen] = useState<boolean>(false);

  useEffect(() => {
    // Parse the query parameters
    const searchParams = new URLSearchParams(location.search);
    const invitedBy = searchParams.get('invitedBy');

    if (invitedBy) {
      handleInvite(invitedBy);
    }

    // Fetch cities data
    fetch("http://localhost:8081/cities")
      .then((res) => res.json())
      .then((data) => setCities(data))
      .catch((error) => console.error("Error fetching cities:", error));
  }, [location]);

  const handleInvite = async (username: string) => {
    try {
      const response = await fetch(`http://localhost:8083/user-invite/${username}`);
      if (!response.ok) {
        if (response.status === 404) {
          throw new Error("User not found");
        }
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const highScore = await response.json();
      console.log("Invited user's high score:", highScore);
      username = username.split("-")[0];
      setInviteMessage(`You were invited by ${username}! Their high score is ${highScore}.`);
      setIsDialogOpen(true);
    } catch (error) {
      console.error("Error fetching invite data:", error);
      setInviteMessage(`Error: ${error}`);
      setIsDialogOpen(true);
    }

  }

  const handleToggle = (city: string) => {
    setSelectedCities((prev) =>
      prev.includes(city) ? prev.filter((c) => c !== city) : [...prev, city]
    );
  };
  const handleStart = () => {
    if (selectedCities.length > 0) {
      navigate("/game", { state: { selectedCities } });
    }
  };
  return (
    <>
      {/* Username Popup */}
      <Dialog open={isDialogOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>{inviteMessage}</DialogTitle>
          </DialogHeader>

          <DialogClose asChild>
            <Button onClick={() => setIsDialogOpen(false)}>Close</Button>
          </DialogClose>
        </DialogContent>
      </Dialog>
      <div className={`flex flex-col items-center justify-center min-h-screen p-6 ${darkMode ? "bg-gray-900 text-white" : "bg-white text-black"}`}>
        <div className="absolute top-4 right-4">
          <Switch checked={darkMode} onCheckedChange={toggleDarkMode} />
        </div>
        <img src={globeGif} alt="Spinning Globe" className="w-32 h-32 mb-4 object-contain" />
        <h1 className="text-3xl font-bold mb-4">Welcome to Globetrotter 🌍</h1>
        <p className="text-sm mb-4">Select cities to get started:</p>
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-2 mb-4">
          {cities.map(({ city }) => (
            <Toggle
              key={city}
              pressed={selectedCities.includes(city)}
              onPressedChange={() => handleToggle(city)}
              className="px-4 py-2"
            >
              {city}
            </Toggle>
          ))}
        </div>
        <Button className="mt-4 px-6 py-2" onClick={handleStart}>Start</Button>
      </div>
    </>
  );
}
