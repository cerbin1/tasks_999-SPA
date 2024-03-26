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
        loadNumberOfTasksChart(data);
      })
      .catch(error => {
        alert(error)
      });

  }, []);

  function loadNumberOfTasksChart(data) {
    const ctx = document.getElementById('numberOfTasksChart');

    if (window.numberOfTasksChart && typeof window.numberOfTasksChart.destroy === 'function') {
      window.numberOfTasksChart.destroy();
    }

    window.numberOfTasksChart = new Chart(ctx, {
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
    <canvas id="numberOfTasksChart"></canvas>
  </div>
}

export default Stats;
