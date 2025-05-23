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

    document.querySelectorAll('.btn-edit-account').forEach(button => {
        button.addEventListener('click', () => {
            const idAccount = button.getAttribute('data-idAccount');
            const idRoles = button.getAttribute('data-idRoles');
            const rolesName = button.getAttribute('data-roles');
            const username = button.getAttribute('data-username');
            const email = button.getAttribute('data-email');
            document.getElementById('idAccount').value = idAccount;
            document.getElementById('rolesId').value = idRoles;
            document.getElementById('username').value = username;
            document.getElementById('email').value = email;
        });
    });

    document.querySelectorAll('.edit-button-client').forEach(button => {
        button.addEventListener('click', () => {
            const primaryIdClient = button.getAttribute('data-primaryIdClient');
            const clientId = button.getAttribute('data-clientId');
            const clientSecret = button.getAttribute('data-clientSecret');
            const scopes = button.getAttribute('data-scopes');
            const redirectUri = button.getAttribute('data-redirectUri');
            const accessTokenHours = button.getAttribute('data-accessTokenHours');
            const refreshTokenDays = button.getAttribute('data-refreshTokenDays');
            document.getElementById('primaryIdClient').value = primaryIdClient;
            document.getElementById('clientId').value = clientId;
            document.getElementById('clientSecret').value = clientSecret;
            document.getElementById('scopes').value = scopes;
            document.getElementById('redirectUri').value = redirectUri;
            document.getElementById('accessTokenHours').value = accessTokenHours;
            document.getElementById('refreshTokenDays').value = refreshTokenDays;
        });
    })

    document.querySelectorAll('.delete-button').forEach(button => {
       button.addEventListener('click', () => {
            const roleId = button.getAttribute("data-id");
            const confirmDelete = confirm(`are you sure you want delete this role ?`);
            if (confirmDelete){
                window.location.href = `/delete-role/${roleId}`;
            }
       });
    });

    document.querySelectorAll('.btn-delete-account').forEach(button => {
        button.addEventListener('click', () => {
            const idAccount = button.getAttribute('data-idAccount');
            const confirmDelete = confirm(`are you sure, you want delete this account ?`);
            if (confirmDelete){
                window.location.href = `delete-account/${idAccount}`;
            }
        });
    });

    document.querySelectorAll('.delete-button-client').forEach(button => {
        button.addEventListener('click', () => {
            const idClientPrimary = button.getAttribute('data-primaryIdClient');
            const confirmDelete = confirm(`are you sure, you want delete this client ?`);
            if (confirmDelete){
                window.location.href = `/delete-client/${idClientPrimary}`;
            }
        }) ;
    });
});
