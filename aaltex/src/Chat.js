import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { TextField, IconButton, List, ListItem, ListItemText, ListItemAvatar, Avatar, Typography } from '@mui/material';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const AppChat = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [nickname, setNickname] = useState('');
  const [stompClient, setStompClient] = useState(null);
  
  const { id } = useParams();
  async function getUserNickname(id) {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/login/users/${id}`);
      if (!response.ok) {
        throw new Error('Failed to fetch user');
      }
      const nickname = await response.text(); // Extract the nickname from the response
      setNickname(nickname); // Set the nickname state
    } catch (error) {
      console.error(error.message);
    }
  };
  useEffect(() => {
  

    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket
    });

    client.onConnect = () => {
      client.subscribe('/topic/messages', (message) => {
        const receivedMessage = JSON.parse(message.body);
        setMessages(prevMessages => [...prevMessages, receivedMessage]);
      });
    };

    client.onStompError = (error) => {
      console.error('STOMP Error:', error);
    };
   
    client.activate();
    setStompClient(client);

    getUserNickname(id);

    return () => {
      client.deactivate();
    };
  }, []);

  const handleNicknameChange = (event) => {
    setNickname(event.target.value);
  };

  const handleMessageChange = (event) => {
    setMessage(event.target.value);
  };

  const sendMessage = () => {
    if (message.trim() && stompClient && stompClient.connected) {
      const chatMessage = {
        nickname,
        content: message,
      };

      stompClient.publish({
        destination: '/app/chat',
        body: JSON.stringify(chatMessage)
      });
      setMessage('');
    }
  };

  return (
    <div>
      <List>
        {messages.map((msg, index) => (
          <ListItem key={index}>
            <ListItemAvatar>
              <Avatar>{msg.nickname.charAt(0)}</Avatar>
            </ListItemAvatar>
            <ListItemText
              primary={<Typography variant="subtitle1">{msg.nickname}</Typography>}
              secondary={msg.content}
            />
          </ListItem>
        ))}
      </List>

      <div style={{ display: 'flex', alignItems: 'center' }}>
        <TextField
          placeholder="user doesnt have the name"
          value={nickname}
          disabled
        />
        <TextField
          placeholder="Type a message"
          value={message}
          onChange={handleMessageChange}
          fullWidth
        />
        <IconButton onClick={sendMessage} disabled={!message.trim()}>
          send
        </IconButton>
      </div>
    </div>
  );
};

export default AppChat;
