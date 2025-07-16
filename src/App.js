import React, { useState } from 'react';
import './styles/global.css';
import './components/Header/Header.css';
import './components/Button/Button.css';
import './components/Card/Card.css';
import './components/Footer/Footer.css';

// Simple Icon for Back (since we don't have MUI icons)
function BackIcon() {
  return (
    <svg width="24" height="24" fill="none" stroke="currentColor" strokeWidth="2">
      <polyline points="15 18 9 12 15 6" />
    </svg>
  );
}

function MainScreen({ onEditClick, onAddClick }) {
  return (
    <div
      style={{
        background: '#DFF2D8',
        borderRadius: '1.2rem',
        padding: '1.5rem 1rem',
        margin: '1rem 0',
        boxShadow: '0 2px 12px rgba(33,150,243,0.08)',
        minHeight: 400,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
      }}
    >
      <div style={{ width: '100%' }}>
        <div style={{ fontSize: 18, fontWeight: 500, color: '#1769aa', textAlign: 'left' }}>Vacation 🔽</div>
        <div style={{ fontWeight: 700, color: '#1769aa', textAlign: 'right', fontSize: 14 }}>00 DAYS</div>
      </div>
      <div style={{ height: 24 }} />
      <div style={{ fontSize: 40, fontWeight: 700, color: '#2196f3', textAlign: 'center' }}>43%</div>
      <div style={{ height: 16 }} />
      <div style={{ fontWeight: 700, color: '#1769aa', textAlign: 'center' }}>TARGET: $00.00</div>
      <div style={{ fontSize: 20, fontWeight: 700, color: '#43ea7f', textAlign: 'center' }}>$00.00 SAVED</div>
      <div style={{ height: 16 }} />
      <button className="btn btn-secondary" style={{ width: '100%' }} onClick={onEditClick}>
        EDIT GOALS
      </button>
      <div style={{ height: 8 }} />
      <button className="btn btn-outline" style={{ width: '100%' }}>
        VIEW ALL GOALS
      </button>
      <div style={{ height: 8 }} />
      <button className="btn btn-secondary" style={{ width: '100%' }} onClick={onAddClick}>
        ADD NEW GOAL
      </button>
    </div>
  );
}

function EditGoalScreen({ onBack }) {
  return (
    <div
      style={{
        background: '#fff',
        borderRadius: '1.2rem',
        padding: '1.5rem 1rem',
        margin: '1rem 0',
        boxShadow: '0 2px 12px rgba(33,150,243,0.08)',
        minHeight: 400,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
      }}
    >
      <div style={{ fontSize: 24, fontWeight: 700, color: '#2196f3', marginBottom: 16 }}>EDIT GOAL</div>
      <div style={{ fontSize: 18, color: '#1769aa', marginBottom: 16 }}>Vacation 🔽</div>
      <div style={{ fontWeight: 700, color: '#1769aa' }}>Target Savings:</div>
      <div>$00.00</div>
      <div style={{ height: 8 }} />
      <div style={{ fontWeight: 700, color: '#1769aa' }}>Time remaining:</div>
      <div>00 DAYS</div>
      <div style={{ height: 8 }} />
      <div style={{ fontWeight: 700, color: '#1769aa' }}>Amount Saved:</div>
      <button className="btn btn-secondary" style={{ width: '100%' }}>$00.00</button>
      <div style={{ flex: 1 }} />
      <button
        className="btn btn-outline"
        style={{ width: 44, height: 44, borderRadius: '50%', padding: 0, display: 'flex', alignItems: 'center', justifyContent: 'center' }}
        onClick={onBack}
        aria-label="Back"
      >
        <BackIcon />
      </button>
    </div>
  );
}

function AddGoalScreen({ onBack }) {
  const [goalText, setGoalText] = useState('');
  return (
    <div
      style={{
        background: '#fff',
        borderRadius: '1.2rem',
        padding: '1.5rem 1rem',
        margin: '1rem 0',
        boxShadow: '0 2px 12px rgba(33,150,243,0.08)',
        minHeight: 400,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center'
      }}
    >
      <div style={{ fontSize: 24, fontWeight: 700, color: '#2196f3', marginBottom: 8 }}>ADD NEW GOALS</div>
      <div style={{ fontWeight: 700, color: '#1769aa' }}>What to include in your goal:</div>
      <div style={{ color: '#1769aa', marginBottom: 16, fontSize: 14 }}>
        • time limit<br />• target savings amount<br />• name of goal
      </div>
      <textarea
        value={goalText}
        onChange={e => setGoalText(e.target.value)}
        placeholder="Type goal here..."
        style={{
          width: '100%',
          minHeight: 100,
          borderRadius: 12,
          border: '1px solid #ced4da',
          padding: 12,
          fontSize: 16,
          marginBottom: 16,
          fontFamily: 'inherit',
          resize: 'vertical'
        }}
      />
      <button className="btn btn-secondary" style={{ width: '100%' }}>
        CREATE GOAL
      </button>
      <div style={{ flex: 1 }} />
      <button
        className="btn btn-outline"
        style={{ width: 44, height: 44, borderRadius: '50%', padding: 0, display: 'flex', alignItems: 'center', justifyContent: 'center' }}
        onClick={onBack}
        aria-label="Back"
      >
        <BackIcon />
      </button>
    </div>
  );
}

function BottomNav({ onNav }) {
  return (
    <nav className="bottom-nav">
      <button className="bottom-nav-link" onClick={() => onNav('main')}>Home</button>
      <button className="bottom-nav-link" onClick={() => onNav('edit')}>Edit</button>
      <button className="bottom-nav-link" onClick={() => onNav('add')}>Add</button>
    </nav>
  );
}

function App() {
  const [currentScreen, setCurrentScreen] = useState('main');

  let content;
  if (currentScreen === 'main') {
    content = <MainScreen onEditClick={() => setCurrentScreen('edit')} onAddClick={() => setCurrentScreen('add')} />;
  } else if (currentScreen === 'edit') {
    content = <EditGoalScreen onBack={() => setCurrentScreen('main')} />;
  } else if (currentScreen === 'add') {
    content = <AddGoalScreen onBack={() => setCurrentScreen('main')} />;
  }

  return (
    <>
      <header className="header">
        <div className="header-container">
          <span className="logo">Goal Tracker</span>
        </div>
      </header>
      <div className="app-main-container" style={{ paddingBottom: 72 }}>
        {content}
      </div>
      <BottomNav onNav={setCurrentScreen} />
    </>
  );
}

export default App;
