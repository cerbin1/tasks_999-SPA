import React, { useState, useEffect } from 'react';
import Chart from 'chart.js/auto';

function Stats() {

  const apiUrl = 'http://localhost:8080/api/stats';

  useEffect(() => {
    fetch(apiUrl, {
      headers: {
        "Authorization": `Bearer ` + localStorage.getItem('token'),
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log(data)
        loadChart(data);
      })
      .catch(error => {
        alert(error)
      });

  }, []);

  function loadChart(data) {
    const ctx = document.getElementById('myChart');
 
    if (window.myChart && typeof window.myChart.destroy === 'function') {
      window.myChart.destroy(); // Destroy the existing chart
    }

    window.myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: data.map(row => row.date),
        datasets: [{
          label: '# of tasks',
          data: data.map(row => row.count),
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }


  return <div>

    <canvas id="myChart"></canvas>
  </div>
}

export default Stats;
