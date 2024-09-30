import React, { useEffect, useState } from 'react';
const apiUrl = process.env.REACT_APP_API_URL;
const AuthPage = () => {
    const [deviceCode, setDeviceCode] = useState('');
    const [ghuKey, setGhuKey] = useState('Waiting for log in...');

    useEffect(() => {
        // Fetch device code
        fetch(`${apiUrl}/auth/get-device-key`)
            .then(response => response.text())
            .then(data => setDeviceCode(data))
            .catch(error => console.error('Error fetching device code:', error));

        // Fetch GhuKey every 5 seconds
        const interval = setInterval(() => {
            fetch(`${apiUrl}/auth/get-ghutoken`)
                .then(response => response.text())
                .then(data => setGhuKey(data))
                .catch(error => console.error('Error fetching GhuKey:', error));
        }, 5000);

        // Cleanup interval on component unmount
        return () => clearInterval(interval);
    }, []);

    const copyToClipboard = (text) => {
        navigator.clipboard.writeText(text).then(() => {
            alert('Copied to clipboard');
        }).catch(err => {
            console.error('Error copying to clipboard:', err);
        });
    };

    return (
        <div>
            <h1>Get Copilot Auth</h1>
            <p>Open login url: <a href="https://github.com/login/device" target="_blank" rel="noopener noreferrer">https://github.com/login/device</a></p>
            <p>Device Code: <span className="clickable" onClick={() => copyToClipboard(deviceCode)}>{deviceCode}</span></p>
            <p>{ghuKey.startsWith('Waiting') ? ghuKey : `GhuKey: `}<span className="clickable" onClick={() => copyToClipboard(ghuKey)}>{ghuKey.startsWith('Waiting') ? '' : ghuKey}</span></p>
        </div>
    );
};

export default AuthPage;