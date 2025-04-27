function downloadCsv() {
    const period = document.getElementById('period').value;
    const timeSheet = document.getElementById('timeSheet').value;

    if (!timeSheet) {
        alert('Пожалуйста, введите табельный номер пользователя перед скачиванием CSV.');
        return;
    }

    window.location.href = `/records/download?period=${encodeURIComponent(period)}&timeSheet=${encodeURIComponent(timeSheet)}`;
}
