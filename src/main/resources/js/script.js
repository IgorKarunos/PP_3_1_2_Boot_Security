document.addEventListener('DOMContentLoaded', function () {
    // ===== Обработчик модалки редактирования =====
    document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            const btn = event.currentTarget;
            const tableRow = btn.closest('tr');
            const modalId = btn.getAttribute('data-bs-target');
            const modal = document.querySelector(modalId);

            // Получаем данные из строки таблицы
            const userId = tableRow.querySelector('td:nth-child(1)').textContent.trim();
            const name = tableRow.querySelector('td:nth-child(2)').textContent.trim();
            const email = tableRow.querySelector('td:nth-child(3)').textContent.trim();
            const rolesString = tableRow.querySelector('td:nth-child(4)').textContent.trim();

            // Заполняем форму в модальном окне
            const form = modal.querySelector('form');
            form.querySelector('input[name="id"]').value = userId;
            form.querySelector('input[name="name"]').value = name;
            form.querySelector('input[name="email"]').value = email;
            form.querySelector('input[name="password"]').value = '';

            // Обновляем выбранные роли
            const rolesSelect = form.querySelector('select[name="roles"]');
            const currentRoles = rolesString.split(',').map(r => r.trim()).filter(Boolean);
            Array.from(rolesSelect.options).forEach(option => {
                option.selected = currentRoles.includes(option.text.trim());
            });
        });
    });

    // ===== Обработчик модалки удаления =====
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function (event) {
            const btn = event.currentTarget;
            const tableRow = btn.closest('tr');
            const modalId = btn.getAttribute('data-bs-target');
            const modal = document.querySelector(modalId);

            // Получаем данные из строки таблицы (Name, Email, Roles)
            const name = tableRow.querySelector('td:nth-child(2)').textContent.trim();
            const email = tableRow.querySelector('td:nth-child(3)').textContent.trim();
            const roles = tableRow.querySelector('td:nth-child(4)').textContent.trim();

            // Заполняем поля внутри формы текущего модала
            const form = modal.querySelector('form');
            const nameInput = form.querySelector('input[name="name"]');
            const emailInput = form.querySelector('input[name="email"]');
            const rolesInput = form.querySelector('input[name="roles"]');

            if (nameInput) nameInput.value = name;
            if (emailInput) emailInput.value = email;
            if (rolesInput) rolesInput.value = roles;
        });
    });
});