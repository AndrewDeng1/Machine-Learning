import React, { useEffect } from 'react';
import socketIOClient from 'socket.io-client';

const App = () => {
  useEffect(() => {
    const socket = socketIOClient('http://localhost:5000'); // Replace with your server URL

    socket.on('udp_packet', (data) => {
      console.log('Received UDP packet:', data);
    });

    return () => {
      socket.disconnect();
    };
  }, []);

  return <div>Listening for UDP packets in the console...</div>;
};

export default App;
