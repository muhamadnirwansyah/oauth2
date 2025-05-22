// custom.js
window.addEventListener('DOMContentLoaded', () => {
    const successModal = document.getElementById("successMessage");
    if (successModal) {
        setTimeout(() => {
            successModal.style.display = 'none';
        }, 3000);
    }

    const successCloseBtn = document.querySelector('#successMessage button');
    if (successCloseBtn) {
        successCloseBtn.addEventListener('click', () => {
            successModal.style.display = 'none';
        });
    }

    const errorCloseBtn = document.querySelector('#errorModal button');
    if (errorCloseBtn) {
        errorCloseBtn.addEventListener('click', () => {
            const errorModal = document.getElementById("errorModal");
            if (errorModal) {
                errorModal.style.display = 'none';
            }
        });
    }

    document.querySelectorAll('.update-button').forEach(button => {
       button.addEventListener('click', () => {
           const id = button.getAttribute("data-id");
           const name = button.getAttribute("data-name");
           document.getElementById("id").value = id;
           document.getElementById("name").value = name;
       });
    });

    document.querySelectorAll('.delete-button').forEach(button => {
       button.addEventListener('click', () => {
            const roleId = button.getAttribute("data-id");
            const confirmDelete = confirm(`are you sure you want delete this role ?`);
            if (confirmDelete){
                window.location.href = `/delete-role/${roleId}`;
            }
       });
    });
});
