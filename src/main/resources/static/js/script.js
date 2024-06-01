
    let dataVid = [];
    let dataDo = [];
    async function fetchDataVid() {
    try {
    const response = await fetch('/api/citiesVid');
    if (!response.ok) {
    throw new Error('помилка');
}
    const jsonData = await response.json();

    const uniqueData = [];
    const seen = new Set();
    for (const item of jsonData) {
    if (!seen.has(item)) {
    uniqueData.push(item);
    seen.add(item);
}
}
    dataVid = uniqueData;
} catch (error) {
    console.error('помилка:', error);
}
}
    async function fetchDataDo() {
    try {
    const response = await fetch('/api/citiesDo');
    if (!response.ok) {
    throw new Error('помилка');
}
    const jsonData = await response.json();

    const uniqueData = [];
    const seen = new Set();
    for (const item of jsonData) {
    if (!seen.has(item)) {
    uniqueData.push(item);
    seen.add(item);
}
}
    dataDo = uniqueData;
} catch (error) {
    console.error('помилка:', error);
}
}
    fetchDataVid();
    fetchDataDo();
    const searchInputVid = document.getElementById('searchInputVid');
    const searchResultsVid = document.getElementById('searchResultsVid');
    const searchInputDo = document.getElementById('searchInputDo');
    const searchResultsDo = document.getElementById('searchResultsDo');
    function showResultsVid(results) {
    searchResultsVid.innerHTML = '';
    results.forEach(result => {
    const li = document.createElement('li');
    li.textContent = result;
    searchResultsVid.appendChild(li);
});
}
    searchInputVid.addEventListener('input', () => {
    const query = searchInputVid.value.toLowerCase();
    const filteredResults = items.filter(item => item.toLowerCase().includes(query));
    showResultsVid(filteredResults);
});
    searchResultsVid.addEventListener('click', (e) => {
    if (e.target.tagName === 'LI') {
    searchInputVid.value = e.target.textContent;
    searchResultsVid.innerHTML = '';
}
});

    function showResultsDo(results) {
    searchResultsDo.innerHTML = '';
    results.forEach(result => {
    const li = document.createElement('li');
    li.textContent = result;
    searchResultsDo.appendChild(li);
});
}
    searchInputDo.addEventListener('input', () => {
    const query = searchInputDo.value.toLowerCase();
    const filteredResults = items.filter(item => item.toLowerCase().includes(query));
    showResultsVid(filteredResults);
});
    searchResultsDo.addEventListener('click', (e) => {
    if (e.target.tagName === 'LI') {
    searchInputDo.value = e.target.textContent;
    searchResultsDo.innerHTML = '';
}
});

    function searchVid() {
    const input = document.getElementById('searchInputVid').value.toLowerCase();
    const resultsList = document.getElementById('searchResultsVid');
    resultsList.innerHTML = '';

    const filteredData = dataVid.filter(item => item.toLowerCase().includes(input));
    filteredData.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item;
    resultsList.appendChild(li);
});

    if (input.length > 0) {
    resultsList.style.display = 'block';
} else {
    resultsList.style.display = 'none';
}
}
    function searchDo() {
    const input = document.getElementById('searchInputDo').value.toLowerCase();
    const resultsList = document.getElementById('searchResultsDo');
    resultsList.innerHTML = '';

    const filteredData = dataDo.filter(item => item.toLowerCase().includes(input));
    filteredData.forEach(item => {
    const li = document.createElement('li');
    li.textContent = item;
    resultsList.appendChild(li);
});

    if (input.length > 0) {
    resultsList.style.display = 'block';
} else {
    resultsList.style.display = 'none';
}
}
